package de.artemis.thinlogs.common.registration;

import de.artemis.thinlogs.common.blocks.ThinLogBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public final class ModBlocks {
    private static final List<ThinLogSet> THIN_LOG_SETS = new ArrayList<>();
    private static final Set<String> EXPECTED_VANILLA_THIN_IDS = Set.of(
            "thin_oak_log",
            "thin_spruce_log",
            "thin_birch_log",
            "thin_jungle_log",
            "thin_acacia_log",
            "thin_dark_oak_log",
            "thin_mangrove_log",
            "thin_cherry_log",
            "thin_bamboo_block",
            "thin_crimson_stem",
            "thin_warped_stem"
    );

    public static final ThinLogSet OAK = registerSet(ThinLogSetDefinition.log("oak", () -> Blocks.OAK_LOG, () -> Blocks.STRIPPED_OAK_LOG, () -> Blocks.OAK_PLANKS));
    public static final ThinLogSet BIRCH = registerSet(ThinLogSetDefinition.log("birch", () -> Blocks.BIRCH_LOG, () -> Blocks.STRIPPED_BIRCH_LOG, () -> Blocks.BIRCH_PLANKS));
    public static final ThinLogSet SPRUCE = registerSet(ThinLogSetDefinition.log("spruce", () -> Blocks.SPRUCE_LOG, () -> Blocks.STRIPPED_SPRUCE_LOG, () -> Blocks.SPRUCE_PLANKS));
    public static final ThinLogSet DARK_OAK = registerSet(ThinLogSetDefinition.log("dark_oak", () -> Blocks.DARK_OAK_LOG, () -> Blocks.STRIPPED_DARK_OAK_LOG, () -> Blocks.DARK_OAK_PLANKS));
    public static final ThinLogSet ACACIA = registerSet(ThinLogSetDefinition.log("acacia", () -> Blocks.ACACIA_LOG, () -> Blocks.STRIPPED_ACACIA_LOG, () -> Blocks.ACACIA_PLANKS));
    public static final ThinLogSet JUNGLE = registerSet(ThinLogSetDefinition.log("jungle", () -> Blocks.JUNGLE_LOG, () -> Blocks.STRIPPED_JUNGLE_LOG, () -> Blocks.JUNGLE_PLANKS));
    public static final ThinLogSet MANGROVE = registerSet(ThinLogSetDefinition.log("mangrove", () -> Blocks.MANGROVE_LOG, () -> Blocks.STRIPPED_MANGROVE_LOG, () -> Blocks.MANGROVE_PLANKS));
    public static final ThinLogSet CHERRY = registerSet(ThinLogSetDefinition.log("cherry", () -> Blocks.CHERRY_LOG, () -> Blocks.STRIPPED_CHERRY_LOG, () -> Blocks.CHERRY_PLANKS));
    public static final ThinLogSet BAMBOO = registerSet(ThinLogSetDefinition.bamboo(() -> Blocks.BAMBOO_BLOCK, () -> Blocks.STRIPPED_BAMBOO_BLOCK, () -> Blocks.BAMBOO_PLANKS));
    public static final ThinLogSet CRIMSON = registerSet(ThinLogSetDefinition.stem("crimson", () -> Blocks.CRIMSON_STEM, () -> Blocks.STRIPPED_CRIMSON_STEM, () -> Blocks.CRIMSON_PLANKS));
    public static final ThinLogSet WARPED = registerSet(ThinLogSetDefinition.stem("warped", () -> Blocks.WARPED_STEM, () -> Blocks.STRIPPED_WARPED_STEM, () -> Blocks.WARPED_PLANKS));

    static {
        validateVanillaCoverage();
    }

    private ModBlocks() {
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> blockSupplier) {
        DeferredBlock<T> registeredBlock = Registration.BLOCKS.register(name, blockSupplier);
        Registration.ITEMS.register(name, () -> new BlockItem(registeredBlock.get(), new Item.Properties()));
        return registeredBlock;
    }

    public static ThinLogSet registerSet(ThinLogSetDefinition definition) {
        @SuppressWarnings("unchecked")
        DeferredBlock<ThinLogBlock>[] strippedHolder = new DeferredBlock[1];

        DeferredBlock<ThinLogBlock> thinBlock = registerBlock(definition.thinId(), () -> new ThinLogBlock(definition.createProperties(), false, () -> strippedHolder[0].get()));
        DeferredBlock<ThinLogBlock> strippedThinBlock = registerBlock(definition.strippedThinId(), () -> new ThinLogBlock(definition.createProperties(), true, null));
        strippedHolder[0] = strippedThinBlock;

        ThinLogSet set = new ThinLogSet(definition, thinBlock, strippedThinBlock);
        THIN_LOG_SETS.add(set);
        return set;
    }

    public static List<ThinLogSet> allSets() {
        return List.copyOf(THIN_LOG_SETS);
    }

    public static Block[] allThinLogBlocks() {
        return THIN_LOG_SETS.stream()
                .flatMap(set -> set.allBlocks().stream())
                .toArray(Block[]::new);
    }

    private static void validateVanillaCoverage() {
        Set<String> registeredThinIds = new LinkedHashSet<>();
        for (ThinLogSet set : THIN_LOG_SETS) {
            if (!registeredThinIds.add(set.definition().thinId())) {
                throw new IllegalStateException("Duplicate thin log set registered for id " + set.definition().thinId());
            }
        }

        if (!registeredThinIds.equals(EXPECTED_VANILLA_THIN_IDS)) {
            Set<String> missing = new LinkedHashSet<>(EXPECTED_VANILLA_THIN_IDS);
            missing.removeAll(registeredThinIds);

            Set<String> unexpected = new LinkedHashSet<>(registeredThinIds);
            unexpected.removeAll(EXPECTED_VANILLA_THIN_IDS);

            throw new IllegalStateException("Thin log set coverage mismatch. Missing=" + missing + ", unexpected=" + unexpected);
        }
    }

    public static void register() {
    }
}
