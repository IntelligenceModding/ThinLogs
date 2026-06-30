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

public class ModDatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, context -> {
                ModConfiguredFeatures.bootstrap(context);
                ModTreeConfiguredFeatures.bootstrap(context);
            })
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);

    public ModDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(ThinLogs.MOD_ID));
    }
}
