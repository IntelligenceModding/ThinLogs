package de.artemis.thinlogs.common.registration;

import de.artemis.thinlogs.ThinLogs;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public final class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> THIN_DARK_FOREST_VEGETATION = key("thin_dark_forest_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> THIN_MANGROVE_VEGETATION = key("thin_mangrove_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> THIN_MEADOW_TREES = key("thin_meadow_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> THIN_TREES_BIRCH_AND_OAK = key("thin_trees_birch_and_oak");
    public static final ResourceKey<ConfiguredFeature<?, ?>> THIN_TREES_FLOWER_FOREST = key("thin_trees_flower_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> THIN_TREES_GROVE = key("thin_trees_grove");
    public static final ResourceKey<ConfiguredFeature<?, ?>> THIN_TREES_JUNGLE = key("thin_trees_jungle");
    public static final ResourceKey<ConfiguredFeature<?, ?>> THIN_TREES_OLD_GROWTH_PINE_TAIGA = key("thin_trees_old_growth_pine_taiga");
    public static final ResourceKey<ConfiguredFeature<?, ?>> THIN_TREES_OLD_GROWTH_SPRUCE_TAIGA = key("thin_trees_old_growth_spruce_taiga");
    public static final ResourceKey<ConfiguredFeature<?, ?>> THIN_TREES_PLAINS = key("thin_trees_plains");
    public static final ResourceKey<ConfiguredFeature<?, ?>> THIN_TREES_SAVANNA = key("thin_trees_savanna");
    public static final ResourceKey<ConfiguredFeature<?, ?>> THIN_TREES_SPARSE_JUNGLE = key("thin_trees_sparse_jungle");
    public static final ResourceKey<ConfiguredFeature<?, ?>> THIN_TREES_TAIGA = key("thin_trees_taiga");
    public static final ResourceKey<ConfiguredFeature<?, ?>> THIN_TREES_WINDSWEPT_HILLS = key("thin_trees_windswept_hills");

    private ModConfiguredFeatures() {
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        register(context, THIN_DARK_FOREST_VEGETATION, Feature.RANDOM_SELECTOR, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_OAK_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_DARK_OAK_CHECKED, 0.6666667F),
                entry(placedFeatures, ModPlacedFeatures.THIN_BIRCH_CHECKED, 0.2F),
                entry(placedFeatures, ModPlacedFeatures.THIN_FANCY_OAK_CHECKED, 0.1F)
        ));
        register(context, THIN_MANGROVE_VEGETATION, Feature.RANDOM_SELECTOR, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_MANGROVE_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_TALL_MANGROVE_CHECKED, 0.85F)
        ));
        register(context, THIN_MEADOW_TREES, Feature.RANDOM_SELECTOR, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_SUPER_BIRCH_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_FANCY_OAK_CHECKED, 0.5F)
        ));
        register(context, THIN_TREES_BIRCH_AND_OAK, Feature.RANDOM_SELECTOR, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_OAK_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_BIRCH_CHECKED, 0.2F),
                entry(placedFeatures, ModPlacedFeatures.THIN_FANCY_OAK_CHECKED, 0.1F)
        ));
        register(context, THIN_TREES_FLOWER_FOREST, Feature.RANDOM_SELECTOR, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_OAK_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_BIRCH_CHECKED, 0.2F),
                entry(placedFeatures, ModPlacedFeatures.THIN_FANCY_OAK_CHECKED, 0.1F)
        ));
        register(context, THIN_TREES_GROVE, Feature.RANDOM_SELECTOR, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_SPRUCE_ON_SNOW,
                entry(placedFeatures, ModPlacedFeatures.THIN_PINE_ON_SNOW, 0.33333334F)
        ));
        register(context, THIN_TREES_JUNGLE, Feature.RANDOM_SELECTOR, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_JUNGLE_TREE_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_FANCY_OAK_CHECKED, 0.1F)
        ));
        register(context, THIN_TREES_OLD_GROWTH_PINE_TAIGA, Feature.RANDOM_SELECTOR, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_SPRUCE_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_PINE_CHECKED, 0.6F)
        ));
        register(context, THIN_TREES_OLD_GROWTH_SPRUCE_TAIGA, Feature.RANDOM_SELECTOR, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_SPRUCE_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_PINE_CHECKED, 0.5F)
        ));
        register(context, THIN_TREES_PLAINS, Feature.RANDOM_SELECTOR, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_OAK_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_FANCY_OAK_CHECKED, 0.33333334F)
        ));
        register(context, THIN_TREES_SAVANNA, Feature.RANDOM_SELECTOR, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_OAK_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_ACACIA_CHECKED, 0.8F)
        ));
        register(context, THIN_TREES_SPARSE_JUNGLE, Feature.RANDOM_SELECTOR, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_JUNGLE_TREE_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_FANCY_OAK_CHECKED, 0.1F)
        ));
        register(context, THIN_TREES_TAIGA, Feature.RANDOM_SELECTOR, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_SPRUCE_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_PINE_CHECKED, 0.33333334F)
        ));
        register(context, THIN_TREES_WINDSWEPT_HILLS, Feature.RANDOM_SELECTOR, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_OAK_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_SPRUCE_CHECKED, 0.666F),
                entry(placedFeatures, ModPlacedFeatures.THIN_FANCY_OAK_CHECKED, 0.1F)
        ));
    }

    private static RandomFeatureConfiguration selector(
            HolderGetter<PlacedFeature> placedFeatures,
            ResourceKey<PlacedFeature> defaultFeature,
            WeightedPlacedFeature... features
    ) {
        return new RandomFeatureConfiguration(List.of(features), placedFeatures.getOrThrow(defaultFeature));
    }

    private static WeightedPlacedFeature entry(HolderGetter<PlacedFeature> placedFeatures, ResourceKey<PlacedFeature> key, float chance) {
        return new WeightedPlacedFeature(placedFeatures.getOrThrow(key), chance);
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> key(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(ThinLogs.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstrapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> key,
            F feature,
            FC configuration
    ) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
