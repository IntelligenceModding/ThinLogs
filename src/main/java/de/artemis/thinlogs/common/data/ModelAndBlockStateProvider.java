package de.artemis.thinlogs.common.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.artemis.thinlogs.ThinLogs;
import de.artemis.thinlogs.common.blockStateProperties.CoreOrientation;
import de.artemis.thinlogs.common.blockStateProperties.ModBlockStateProperties;
import de.artemis.thinlogs.common.registration.ModBlocks;
import de.artemis.thinlogs.common.registration.ThinLogSet;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class ModelAndBlockStateProvider extends ModelProvider {
    private final PackOutput.PathProvider blockstates;
    private final PackOutput.PathProvider blockModels;
    private final PackOutput.PathProvider items;

    public ModelAndBlockStateProvider(PackOutput output) {
        super(output, ThinLogs.MOD_ID);
        this.blockstates = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
        this.blockModels = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models/block");
        this.items = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "items");
    }

    @Override
    protected void registerModels(@NonNull BlockModelGenerators blockModels, @NonNull ItemModelGenerators itemModels) {
    }

    @Override
    public @NonNull CompletableFuture<?> run(@NonNull CachedOutput output) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (ThinLogSet set : ModBlocks.allSets()) {
            addThinLog(futures, output, set.definition().thinId(), set.definition().sideTexture().toString(), set.definition().endTexture().toString());
            addThinLog(futures, output, set.definition().strippedThinId(), set.definition().strippedSideTexture().toString(), set.definition().strippedEndTexture().toString());
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    private void addThinLog(List<CompletableFuture<?>> futures, CachedOutput output, String id, String sideTexture, String endTexture) {
        futures.add(saveBlockState(output, id));
        futures.add(saveItemModel(output, id));

        for (CoreOrientation orientation : CoreOrientation.values()) {
            futures.add(saveBlockModel(output, id + "_core_" + orientation.getSerializedName(), "generation/branch_core_" + orientation.getSerializedName(), sideTexture, endTexture));
        }

        for (Direction direction : Direction.values()) {
            futures.add(saveBlockModel(output, id + "_arm_" + direction.getSerializedName(), "generation/branch_arm_" + direction.getSerializedName(), sideTexture, endTexture));
        }

        futures.add(saveBlockModel(output, id + "_inventory", "generation/branch_inventory", sideTexture, endTexture));
    }

    private CompletableFuture<?> saveBlockState(CachedOutput output, String id) {
        JsonObject root = new JsonObject();
        JsonArray multipart = new JsonArray();

        for (CoreOrientation orientation : CoreOrientation.values()) {
            JsonObject part = new JsonObject();
            part.add("when", condition(ModBlockStateProperties.CORE_ORIENTATION.getName(), orientation.getSerializedName()));
            part.add("apply", model("block/" + id + "_core_" + orientation.getSerializedName()));
            multipart.add(part);
        }

        for (Direction direction : Direction.values()) {
            JsonObject part = new JsonObject();
            part.add("when", condition(ModBlockStateProperties.connection(direction).getName(), "true"));
            part.add("apply", model("block/" + id + "_arm_" + direction.getSerializedName()));
            multipart.add(part);
        }

        root.add("multipart", multipart);
        return save(output, root, blockstates.json(modId(id)));
    }

    private CompletableFuture<?> saveBlockModel(CachedOutput output, String modelName, String parent, String sideTexture, String endTexture) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", modPath(parent));

        JsonObject textures = new JsonObject();
        textures.addProperty("log_side", sideTexture);
        textures.addProperty("log_top", endTexture);
        root.add("textures", textures);

        return save(output, root, blockModels.json(modId(modelName)));
    }

    private CompletableFuture<?> saveItemModel(CachedOutput output, String id) {
        JsonObject root = new JsonObject();
        JsonObject model = new JsonObject();
        model.addProperty("type", "minecraft:model");
        model.addProperty("model", modPath("block/" + id + "_inventory"));
        root.add("model", model);
        return save(output, root, items.json(modId(id)));
    }

    private static JsonObject condition(String property, String value) {
        JsonObject condition = new JsonObject();
        condition.addProperty(property, value);
        return condition;
    }

    private static JsonObject model(String model) {
        JsonObject apply = new JsonObject();
        apply.addProperty("model", modPath(model));
        return apply;
    }

    private static Identifier modId(String path) {
        return Identifier.fromNamespaceAndPath(ThinLogs.MOD_ID, path);
    }

    private static String modPath(String path) {
        return ThinLogs.MOD_ID + ":" + path;
    }

    private static CompletableFuture<?> save(CachedOutput output, JsonObject json, Path path) {
        return net.minecraft.data.DataProvider.saveStable(output, json, path);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected @NonNull Stream<? extends Holder<Block>> getKnownBlocks() {
        return ModBlocks.allSets().stream()
                .flatMap(set -> Stream.of(
                        set.thinBlock().get().builtInRegistryHolder(),
                        set.strippedThinBlock().get().builtInRegistryHolder()
                ));
    }

    @SuppressWarnings("deprecation")
    @Override
    protected @NonNull Stream<? extends Holder<Item>> getKnownItems() {
        return ModBlocks.allSets().stream()
                .flatMap(set -> Stream.of(
                        set.thinBlock().get().asItem().builtInRegistryHolder(),
                        set.strippedThinBlock().get().asItem().builtInRegistryHolder()
                ));
    }
}
