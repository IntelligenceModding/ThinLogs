package de.artemis.thinlogs.common.data;

import de.artemis.thinlogs.ThinLogs;
import de.artemis.thinlogs.common.registration.ModBiomeModifiers;
import de.artemis.thinlogs.common.registration.ModConfiguredFeatures;
import de.artemis.thinlogs.common.registration.ModPlacedFeatures;
import de.artemis.thinlogs.common.registration.ModTreeConfiguredFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public final class ModDatapackProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.FEATURE, context -> {
                ModConfiguredFeatures.bootstrap(context);
                ModTreeConfiguredFeatures.bootstrap(context);
            })
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);

    public static DatapackBuiltinEntriesProvider world(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        return DatapackBuiltinEntriesProvider.forWorldLayer(output, "Thin Logs World Data", registries, BUILDER, Set.of(ThinLogs.MOD_ID));
    }

    public static DatapackBuiltinEntriesProvider reloadable(PackOutput output, CompletableFuture<HolderLookup.Provider> world,
                                                          CompletableFuture<HolderLookup.Provider> reloadable) {
        RegistrySetBuilder builder = new RegistrySetBuilder()
                .add(Registries.LOOT_TABLE, new BlockLootTablesProvider())
                .add(net.minecraft.data.recipes.RecipeProvider.asBootstrap(RecipesProvider::new));
        return DatapackBuiltinEntriesProvider.forReloadableLayer(output, "Thin Logs Recipes and Loot", world, reloadable,
                builder, Set.of(ThinLogs.MOD_ID, "minecraft"));
    }
}
