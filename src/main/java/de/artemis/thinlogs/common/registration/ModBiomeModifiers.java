package de.artemis.thinlogs.common.registration;

import de.artemis.thinlogs.ThinLogs;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_THIN_CHERRY_GROVE = key("add_thin_cherry_grove");
    public static final ResourceKey<BiomeModifier> ADD_THIN_CRIMSON_FOREST = key("add_thin_crimson_forest");
    public static final ResourceKey<BiomeModifier> ADD_THIN_DARK_FOREST = key("add_thin_dark_forest");
    public static final ResourceKey<BiomeModifier> ADD_THIN_GROVE = key("add_thin_grove");
    public static final ResourceKey<BiomeModifier> ADD_THIN_MANGROVE_SWAMP = key("add_thin_mangrove_swamp");
    public static final ResourceKey<BiomeModifier> ADD_THIN_MEADOW = key("add_thin_meadow");
    public static final ResourceKey<BiomeModifier> ADD_THIN_SNOWY = key("add_thin_snowy");
    public static final ResourceKey<BiomeModifier> ADD_THIN_SWAMP = key("add_thin_swamp");
    public static final ResourceKey<BiomeModifier> ADD_THIN_TREES_BIRCH = key("add_thin_trees_birch");
    public static final ResourceKey<BiomeModifier> ADD_THIN_TREES_FLOWER_FOREST = key("add_thin_trees_flower_forest");
    public static final ResourceKey<BiomeModifier> ADD_THIN_TREES_FOREST = key("add_thin_trees_forest");
    public static final ResourceKey<BiomeModifier> ADD_THIN_TREES_JUNGLE = key("add_thin_trees_jungle");
    public static final ResourceKey<BiomeModifier> ADD_THIN_TREES_OLD_GROWTH_PINE_TAIGA = key("add_thin_trees_old_growth_pine_taiga");
    public static final ResourceKey<BiomeModifier> ADD_THIN_TREES_OLD_GROWTH_SPRUCE_TAIGA = key("add_thin_trees_old_growth_spruce_taiga");
    public static final ResourceKey<BiomeModifier> ADD_THIN_TREES_PLAINS = key("add_thin_trees_plains");
    public static final ResourceKey<BiomeModifier> ADD_THIN_TREES_SAVANNA = key("add_thin_trees_savanna");
    public static final ResourceKey<BiomeModifier> ADD_THIN_TREES_SPARSE_JUNGLE = key("add_thin_trees_sparse_jungle");
    public static final ResourceKey<BiomeModifier> ADD_THIN_TREES_TAIGA = key("add_thin_trees_taiga");
    public static final ResourceKey<BiomeModifier> ADD_THIN_WARPED_FOREST = key("add_thin_warped_forest");
    public static final ResourceKey<BiomeModifier> ADD_THIN_WINDSWEPT_FOREST = key("add_thin_windswept_forest");
    public static final ResourceKey<BiomeModifier> ADD_THIN_WINDSWEPT_HILLS = key("add_thin_windswept_hills");
    public static final ResourceKey<BiomeModifier> ADD_THIN_WOODED_BADLANDS = key("add_thin_wooded_badlands");

    private ModBiomeModifiers() {
    }

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_THIN_CHERRY_GROVE, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.CHERRY_GROVE)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_TREES_CHERRY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_THIN_CRIMSON_FOREST, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.CRIMSON_FOREST)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_CRIMSON_FUNGI)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_THIN_DARK_FOREST, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.DARK_FOREST)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_DARK_FOREST_VEGETATION)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_THIN_GROVE, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.GROVE)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_TREES_GROVE)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_THIN_MANGROVE_SWAMP, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.MANGROVE_SWAMP)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_TREES_MANGROVE)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_THIN_MEADOW, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.MEADOW)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_TREES_MEADOW)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_THIN_SNOWY, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.SNOWY_PLAINS),
                        biomes.getOrThrow(Biomes.ICE_SPIKES)
                ),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_TREES_SNOWY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_THIN_SWAMP, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.SWAMP)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_TREES_SWAMP)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_THIN_TREES_BIRCH, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.BIRCH_FOREST)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_TREES_BIRCH)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_THIN_TREES_FLOWER_FOREST, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.FLOWER_FOREST)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_TREES_FLOWER_FOREST)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_THIN_TREES_FOREST, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.FOREST)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_TREES_BIRCH_AND_OAK)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_THIN_TREES_JUNGLE, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.JUNGLE)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_TREES_JUNGLE)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_THIN_TREES_OLD_GROWTH_PINE_TAIGA, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.OLD_GROWTH_PINE_TAIGA)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_TREES_OLD_GROWTH_PINE_TAIGA)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_THIN_TREES_OLD_GROWTH_SPRUCE_TAIGA, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.OLD_GROWTH_SPRUCE_TAIGA)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_TREES_OLD_GROWTH_SPRUCE_TAIGA)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_THIN_TREES_PLAINS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.PLAINS),
                        biomes.getOrThrow(Biomes.SUNFLOWER_PLAINS),
                        biomes.getOrThrow(Biomes.DEEP_DARK),
                        biomes.getOrThrow(Biomes.DRIPSTONE_CAVES)
                ),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_TREES_PLAINS)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_THIN_TREES_SAVANNA, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.SAVANNA),
                        biomes.getOrThrow(Biomes.SAVANNA_PLATEAU),
                        biomes.getOrThrow(Biomes.WINDSWEPT_SAVANNA)
                ),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_TREES_SAVANNA)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_THIN_TREES_SPARSE_JUNGLE, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.SPARSE_JUNGLE)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_TREES_SPARSE_JUNGLE)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_THIN_TREES_TAIGA, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.TAIGA),
                        biomes.getOrThrow(Biomes.SNOWY_TAIGA)
                ),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_TREES_TAIGA)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_THIN_WARPED_FOREST, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.WARPED_FOREST)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_WARPED_FUNGI)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_THIN_WINDSWEPT_FOREST, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.WINDSWEPT_FOREST)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_TREES_WINDSWEPT_FOREST)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_THIN_WINDSWEPT_HILLS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(Biomes.WINDSWEPT_HILLS),
                        biomes.getOrThrow(Biomes.WINDSWEPT_GRAVELLY_HILLS)
                ),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_TREES_WINDSWEPT_HILLS)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_THIN_WOODED_BADLANDS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.WOODED_BADLANDS)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_TREES_BADLANDS)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }

    private static ResourceKey<BiomeModifier> key(String name) {
        return ResourceKey.create(
                NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                ResourceLocation.fromNamespaceAndPath(ThinLogs.MOD_ID, name)
        );
    }
}
