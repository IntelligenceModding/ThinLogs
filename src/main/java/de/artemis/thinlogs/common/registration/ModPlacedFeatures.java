package de.artemis.thinlogs.common.registration;

import de.artemis.thinlogs.ThinLogs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;

import java.util.List;

public final class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> THIN_OAK_CHECKED = key("thin_oak_checked");
    public static final ResourceKey<PlacedFeature> THIN_BIRCH_CHECKED = key("thin_birch_checked");
    public static final ResourceKey<PlacedFeature> THIN_SPRUCE_CHECKED = key("thin_spruce_checked");
    public static final ResourceKey<PlacedFeature> THIN_PINE_CHECKED = key("thin_pine_checked");
    public static final ResourceKey<PlacedFeature> THIN_JUNGLE_TREE_CHECKED = key("thin_jungle_tree_checked");
    public static final ResourceKey<PlacedFeature> THIN_ACACIA_CHECKED = key("thin_acacia_checked");
    public static final ResourceKey<PlacedFeature> THIN_DARK_OAK_CHECKED = key("thin_dark_oak_checked");
    public static final ResourceKey<PlacedFeature> THIN_FANCY_OAK_CHECKED = key("thin_fancy_oak_checked");
    public static final ResourceKey<PlacedFeature> THIN_SUPER_BIRCH_CHECKED = key("thin_super_birch_checked");
    public static final ResourceKey<PlacedFeature> THIN_SWAMP_OAK_CHECKED = key("thin_swamp_oak_checked");
    public static final ResourceKey<PlacedFeature> THIN_CHERRY_CHECKED = key("thin_cherry_checked");
    public static final ResourceKey<PlacedFeature> THIN_MANGROVE_CHECKED = key("thin_mangrove_checked");
    public static final ResourceKey<PlacedFeature> THIN_TALL_MANGROVE_CHECKED = key("thin_tall_mangrove_checked");
    public static final ResourceKey<PlacedFeature> THIN_PINE_ON_SNOW = key("thin_pine_on_snow");
    public static final ResourceKey<PlacedFeature> THIN_SPRUCE_ON_SNOW = key("thin_spruce_on_snow");
    public static final ResourceKey<PlacedFeature> THIN_CRIMSON_FUNGI = key("thin_crimson_fungi");
    public static final ResourceKey<PlacedFeature> THIN_WARPED_FUNGI = key("thin_warped_fungi");
    public static final ResourceKey<PlacedFeature> THIN_DARK_FOREST_VEGETATION = key("thin_dark_forest_vegetation");
    public static final ResourceKey<PlacedFeature> THIN_TREES_BADLANDS = key("thin_trees_badlands");
    public static final ResourceKey<PlacedFeature> THIN_TREES_BIRCH = key("thin_trees_birch");
    public static final ResourceKey<PlacedFeature> THIN_TREES_BIRCH_AND_OAK = key("thin_trees_birch_and_oak");
    public static final ResourceKey<PlacedFeature> THIN_TREES_CHERRY = key("thin_trees_cherry");
    public static final ResourceKey<PlacedFeature> THIN_TREES_FLOWER_FOREST = key("thin_trees_flower_forest");
    public static final ResourceKey<PlacedFeature> THIN_TREES_GROVE = key("thin_trees_grove");
    public static final ResourceKey<PlacedFeature> THIN_TREES_JUNGLE = key("thin_trees_jungle");
    public static final ResourceKey<PlacedFeature> THIN_TREES_MANGROVE = key("thin_trees_mangrove");
    public static final ResourceKey<PlacedFeature> THIN_TREES_MEADOW = key("thin_trees_meadow");
    public static final ResourceKey<PlacedFeature> THIN_TREES_OLD_GROWTH_PINE_TAIGA = key("thin_trees_old_growth_pine_taiga");
    public static final ResourceKey<PlacedFeature> THIN_TREES_OLD_GROWTH_SPRUCE_TAIGA = key("thin_trees_old_growth_spruce_taiga");
    public static final ResourceKey<PlacedFeature> THIN_TREES_PLAINS = key("thin_trees_plains");
    public static final ResourceKey<PlacedFeature> THIN_TREES_SAVANNA = key("thin_trees_savanna");
    public static final ResourceKey<PlacedFeature> THIN_TREES_SNOWY = key("thin_trees_snowy");
    public static final ResourceKey<PlacedFeature> THIN_TREES_SPARSE_JUNGLE = key("thin_trees_sparse_jungle");
    public static final ResourceKey<PlacedFeature> THIN_TREES_SWAMP = key("thin_trees_swamp");
    public static final ResourceKey<PlacedFeature> THIN_TREES_TAIGA = key("thin_trees_taiga");
    public static final ResourceKey<PlacedFeature> THIN_TREES_WINDSWEPT_FOREST = key("thin_trees_windswept_forest");
    public static final ResourceKey<PlacedFeature> THIN_TREES_WINDSWEPT_HILLS = key("thin_trees_windswept_hills");

    private ModPlacedFeatures() {
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configured = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, THIN_OAK_CHECKED, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_OAK), List.of(
                PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
        ));
        register(context, THIN_BIRCH_CHECKED, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_BIRCH), List.of(
                PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)
        ));
        register(context, THIN_SPRUCE_CHECKED, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_SPRUCE), List.of(
                PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING)
        ));
        register(context, THIN_PINE_CHECKED, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_PINE), List.of(
                PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING)
        ));
        register(context, THIN_JUNGLE_TREE_CHECKED, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_JUNGLE_TREE), List.of(
                PlacementUtils.filteredByBlockSurvival(Blocks.JUNGLE_SAPLING)
        ));
        register(context, THIN_ACACIA_CHECKED, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_ACACIA), List.of(
                PlacementUtils.filteredByBlockSurvival(Blocks.ACACIA_SAPLING)
        ));
        register(context, THIN_DARK_OAK_CHECKED, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_DARK_OAK), List.of(
                PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING)
        ));
        register(context, THIN_FANCY_OAK_CHECKED, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_FANCY_OAK), List.of(
                PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
        ));
        register(context, THIN_SUPER_BIRCH_CHECKED, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_SUPER_BIRCH), List.of(
                PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)
        ));
        register(context, THIN_SWAMP_OAK_CHECKED, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_SWAMP_OAK), List.of(
                PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
        ));
        register(context, THIN_CHERRY_CHECKED, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_CHERRY), List.of(
                PlacementUtils.filteredByBlockSurvival(Blocks.CHERRY_SAPLING)
        ));
        register(context, THIN_MANGROVE_CHECKED, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_MANGROVE), List.of(
                PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE)
        ));
        register(context, THIN_TALL_MANGROVE_CHECKED, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_TALL_MANGROVE), List.of(
                PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE)
        ));

        register(context, THIN_PINE_ON_SNOW, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_PINE), List.of(
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.POWDER_SNOW)), 8),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0), Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW))
        ));
        register(context, THIN_SPRUCE_ON_SNOW, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_SPRUCE), List.of(
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.POWDER_SNOW)), 8),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0), Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW))
        ));

        register(context, THIN_CRIMSON_FUNGI, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_CRIMSON_FUNGUS), List.of(
                CountOnEveryLayerPlacement.of(1),
                BiomeFilter.biome()
        ));
        register(context, THIN_WARPED_FUNGI, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_WARPED_FUNGUS), List.of(
                CountOnEveryLayerPlacement.of(1),
                BiomeFilter.biome()
        ));
        register(context, THIN_DARK_FOREST_VEGETATION, configured.getOrThrow(ModConfiguredFeatures.THIN_DARK_FOREST_VEGETATION), featureScatter(weightedCounts(1, 7, 2, 3), 0, Heightmap.Types.OCEAN_FLOOR));
        register(context, THIN_TREES_BADLANDS, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_OAK), featureScatter(weightedCounts(0, 6, 1, 4), 0, Heightmap.Types.OCEAN_FLOOR));
        register(context, THIN_TREES_BIRCH, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_BIRCH), featureScatter(weightedCounts(1, 9, 2, 1), 0, Heightmap.Types.OCEAN_FLOOR));
        register(context, THIN_TREES_BIRCH_AND_OAK, configured.getOrThrow(ModConfiguredFeatures.THIN_TREES_BIRCH_AND_OAK), featureScatter(weightedCounts(1, 9, 2, 1), 0, Heightmap.Types.OCEAN_FLOOR));
        register(context, THIN_TREES_CHERRY, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_CHERRY), featureScatter(weightedCounts(1, 9, 2, 1), 0, Heightmap.Types.OCEAN_FLOOR));
        register(context, THIN_TREES_FLOWER_FOREST, configured.getOrThrow(ModConfiguredFeatures.THIN_TREES_FLOWER_FOREST), featureScatter(weightedCounts(1, 9, 2, 1), 0, Heightmap.Types.OCEAN_FLOOR));
        register(context, THIN_TREES_GROVE, configured.getOrThrow(ModConfiguredFeatures.THIN_TREES_GROVE), featureScatter(weightedCounts(1, 9, 2, 1), 0, Heightmap.Types.OCEAN_FLOOR));
        register(context, THIN_TREES_JUNGLE, configured.getOrThrow(ModConfiguredFeatures.THIN_TREES_JUNGLE), featureScatter(weightedCounts(2, 9, 3, 1), 0, Heightmap.Types.OCEAN_FLOOR));
        register(context, THIN_TREES_MANGROVE, configured.getOrThrow(ModConfiguredFeatures.THIN_MANGROVE_VEGETATION), featureScatter(CountPlacement.of(2), 5, Heightmap.Types.OCEAN_FLOOR));
        register(context, THIN_TREES_MEADOW, configured.getOrThrow(ModConfiguredFeatures.THIN_MEADOW_TREES), List.of(
                RarityFilter.onAverageOnceEvery(60),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
                BiomeFilter.biome()
        ));
        register(context, THIN_TREES_OLD_GROWTH_PINE_TAIGA, configured.getOrThrow(ModConfiguredFeatures.THIN_TREES_OLD_GROWTH_PINE_TAIGA), featureScatter(weightedCounts(1, 9, 2, 1), 0, Heightmap.Types.OCEAN_FLOOR));
        register(context, THIN_TREES_OLD_GROWTH_SPRUCE_TAIGA, configured.getOrThrow(ModConfiguredFeatures.THIN_TREES_OLD_GROWTH_SPRUCE_TAIGA), featureScatter(weightedCounts(1, 9, 2, 1), 0, Heightmap.Types.OCEAN_FLOOR));
        register(context, THIN_TREES_PLAINS, configured.getOrThrow(ModConfiguredFeatures.THIN_TREES_PLAINS), featureScatter(weightedCounts(0, 9, 1, 1), 0, Heightmap.Types.OCEAN_FLOOR));
        register(context, THIN_TREES_SAVANNA, configured.getOrThrow(ModConfiguredFeatures.THIN_TREES_SAVANNA), featureScatter(weightedCounts(0, 7, 1, 3), 0, Heightmap.Types.OCEAN_FLOOR));
        register(context, THIN_TREES_SNOWY, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_SPRUCE), featureScatter(weightedCounts(0, 8, 1, 2), 0, Heightmap.Types.OCEAN_FLOOR));
        register(context, THIN_TREES_SPARSE_JUNGLE, configured.getOrThrow(ModConfiguredFeatures.THIN_TREES_SPARSE_JUNGLE), featureScatter(weightedCounts(0, 7, 1, 3), 0, Heightmap.Types.OCEAN_FLOOR));
        register(context, THIN_TREES_SWAMP, configured.getOrThrow(ModTreeConfiguredFeatures.THIN_SWAMP_OAK), featureScatter(weightedCounts(0, 7, 1, 3), 2, Heightmap.Types.OCEAN_FLOOR));
        register(context, THIN_TREES_TAIGA, configured.getOrThrow(ModConfiguredFeatures.THIN_TREES_TAIGA), featureScatter(weightedCounts(1, 9, 2, 1), 0, Heightmap.Types.OCEAN_FLOOR));
        register(context, THIN_TREES_WINDSWEPT_FOREST, configured.getOrThrow(ModConfiguredFeatures.THIN_TREES_WINDSWEPT_HILLS), featureScatter(weightedCounts(0, 6, 1, 4), 0, Heightmap.Types.OCEAN_FLOOR));
        register(context, THIN_TREES_WINDSWEPT_HILLS, configured.getOrThrow(ModConfiguredFeatures.THIN_TREES_WINDSWEPT_HILLS), featureScatter(weightedCounts(0, 8, 1, 2), 0, Heightmap.Types.OCEAN_FLOOR));
    }

    private static List<PlacementModifier> featureScatter(PlacementModifier count, int maxWaterDepth, Heightmap.Types heightmap) {
        return List.of(
                count,
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(maxWaterDepth),
                HeightmapPlacement.onHeightmap(heightmap),
                BiomeFilter.biome()
        );
    }

    private static CountPlacement weightedCounts(int firstData, int firstWeight, int secondData, int secondWeight) {
        SimpleWeightedRandomList.Builder<net.minecraft.util.valueproviders.IntProvider> builder = SimpleWeightedRandomList.builder();
        builder.add(ConstantInt.of(firstData), firstWeight);
        builder.add(ConstantInt.of(secondData), secondWeight);
        return CountPlacement.of(new WeightedListInt(builder.build()));
    }

    private static ResourceKey<PlacedFeature> key(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(ThinLogs.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
