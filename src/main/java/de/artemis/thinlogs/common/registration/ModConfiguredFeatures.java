package de.artemis.thinlogs.common.registration;

import de.artemis.thinlogs.ThinLogs;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.RandomSelectorFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public final class ModConfiguredFeatures {
    public static final ResourceKey<Feature> THIN_TREES_POPLAR = key("thin_trees_poplar");
    public static final ResourceKey<Feature> THIN_DARK_FOREST_VEGETATION = key("thin_dark_forest_vegetation");
    public static final ResourceKey<Feature> THIN_MANGROVE_VEGETATION = key("thin_mangrove_vegetation");
    public static final ResourceKey<Feature> THIN_MEADOW_TREES = key("thin_meadow_trees");
    public static final ResourceKey<Feature> THIN_TREES_BIRCH_AND_OAK = key("thin_trees_birch_and_oak");
    public static final ResourceKey<Feature> THIN_TREES_FLOWER_FOREST = key("thin_trees_flower_forest");
    public static final ResourceKey<Feature> THIN_TREES_GROVE = key("thin_trees_grove");
    public static final ResourceKey<Feature> THIN_TREES_JUNGLE = key("thin_trees_jungle");
    public static final ResourceKey<Feature> THIN_TREES_OLD_GROWTH_PINE_TAIGA = key("thin_trees_old_growth_pine_taiga");
    public static final ResourceKey<Feature> THIN_TREES_OLD_GROWTH_SPRUCE_TAIGA = key("thin_trees_old_growth_spruce_taiga");
    public static final ResourceKey<Feature> THIN_TREES_PLAINS = key("thin_trees_plains");
    public static final ResourceKey<Feature> THIN_TREES_SAVANNA = key("thin_trees_savanna");
    public static final ResourceKey<Feature> THIN_TREES_SPARSE_JUNGLE = key("thin_trees_sparse_jungle");
    public static final ResourceKey<Feature> THIN_TREES_TAIGA = key("thin_trees_taiga");
    public static final ResourceKey<Feature> THIN_TREES_WINDSWEPT_HILLS = key("thin_trees_windswept_hills");

    private ModConfiguredFeatures() {
    }

    public static void bootstrap(BootstrapContext<Feature> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        register(context, THIN_TREES_POPLAR, new net.minecraft.world.level.levelgen.feature.WeightedRandomSelectorFeature(
                net.minecraft.util.random.WeightedList.<net.minecraft.core.Holder<PlacedFeature>>builder()
                        .add(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_RED_POPLAR_CHECKED), 1)
                        .add(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_ORANGE_POPLAR_CHECKED), 1)
                        .add(placedFeatures.getOrThrow(ModPlacedFeatures.THIN_YELLOW_POPLAR_CHECKED), 1).build()));

        register(context, THIN_DARK_FOREST_VEGETATION, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_OAK_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_DARK_OAK_CHECKED, 0.6666667F),
                entry(placedFeatures, ModPlacedFeatures.THIN_BIRCH_CHECKED, 0.2F),
                entry(placedFeatures, ModPlacedFeatures.THIN_FANCY_OAK_CHECKED, 0.1F)
        ));
        register(context, THIN_MANGROVE_VEGETATION, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_MANGROVE_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_TALL_MANGROVE_CHECKED, 0.85F)
        ));
        register(context, THIN_MEADOW_TREES, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_SUPER_BIRCH_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_FANCY_OAK_CHECKED, 0.5F)
        ));
        register(context, THIN_TREES_BIRCH_AND_OAK, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_OAK_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_BIRCH_CHECKED, 0.2F),
                entry(placedFeatures, ModPlacedFeatures.THIN_FANCY_OAK_CHECKED, 0.1F)
        ));
        register(context, THIN_TREES_FLOWER_FOREST, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_OAK_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_BIRCH_CHECKED, 0.2F),
                entry(placedFeatures, ModPlacedFeatures.THIN_FANCY_OAK_CHECKED, 0.1F)
        ));
        register(context, THIN_TREES_GROVE, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_SPRUCE_ON_SNOW,
                entry(placedFeatures, ModPlacedFeatures.THIN_PINE_ON_SNOW, 0.33333334F)
        ));
        register(context, THIN_TREES_JUNGLE, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_JUNGLE_TREE_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_FANCY_OAK_CHECKED, 0.1F)
        ));
        register(context, THIN_TREES_OLD_GROWTH_PINE_TAIGA, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_SPRUCE_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_PINE_CHECKED, 0.6F)
        ));
        register(context, THIN_TREES_OLD_GROWTH_SPRUCE_TAIGA, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_SPRUCE_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_PINE_CHECKED, 0.5F)
        ));
        register(context, THIN_TREES_PLAINS, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_OAK_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_FANCY_OAK_CHECKED, 0.33333334F)
        ));
        register(context, THIN_TREES_SAVANNA, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_OAK_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_ACACIA_CHECKED, 0.8F)
        ));
        register(context, THIN_TREES_SPARSE_JUNGLE, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_JUNGLE_TREE_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_FANCY_OAK_CHECKED, 0.1F)
        ));
        register(context, THIN_TREES_TAIGA, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_SPRUCE_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_PINE_CHECKED, 0.33333334F)
        ));
        register(context, THIN_TREES_WINDSWEPT_HILLS, selector(
                placedFeatures,
                ModPlacedFeatures.THIN_OAK_CHECKED,
                entry(placedFeatures, ModPlacedFeatures.THIN_SPRUCE_CHECKED, 0.666F),
                entry(placedFeatures, ModPlacedFeatures.THIN_FANCY_OAK_CHECKED, 0.1F)
        ));
    }

    private static RandomSelectorFeature selector(
            HolderGetter<PlacedFeature> placedFeatures,
            ResourceKey<PlacedFeature> defaultFeature,
            WeightedPlacedFeature... features
    ) {
        return new RandomSelectorFeature(List.of(features), placedFeatures.getOrThrow(defaultFeature));
    }

    private static WeightedPlacedFeature entry(HolderGetter<PlacedFeature> placedFeatures, ResourceKey<PlacedFeature> key, float chance) {
        return new WeightedPlacedFeature(placedFeatures.getOrThrow(key), chance);
    }

    private static ResourceKey<Feature> key(String name) {
        return ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath(ThinLogs.MOD_ID, name));
    }

    private static void register(BootstrapContext<Feature> context, ResourceKey<Feature> key, Feature feature) {
        context.register(key, feature);
    }
}
