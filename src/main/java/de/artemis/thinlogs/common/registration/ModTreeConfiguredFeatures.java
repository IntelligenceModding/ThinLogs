package de.artemis.thinlogs.common.registration;

import de.artemis.thinlogs.ThinLogs;
import de.artemis.thinlogs.common.worldgen.feature.ThinTreeFeature;
import net.minecraft.core.Holder;
import de.artemis.thinlogs.common.worldgen.trunkplacers.ConnectedBranchingTrunkPlacer;
import de.artemis.thinlogs.common.worldgen.trunkplacers.ConnectedForkingTrunkPlacer;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.HugeFungusFeature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.PineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacement;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.Optional;
import java.util.OptionalInt;

public final class ModTreeConfiguredFeatures {
    public static final ResourceKey<Feature> THIN_RED_POPLAR = key("thin_red_poplar");
    public static final ResourceKey<Feature> THIN_ORANGE_POPLAR = key("thin_orange_poplar");
    public static final ResourceKey<Feature> THIN_YELLOW_POPLAR = key("thin_yellow_poplar");
    public static final ResourceKey<Feature> THIN_OAK = key("thin_oak");
    public static final ResourceKey<Feature> THIN_BIRCH = key("thin_birch");
    public static final ResourceKey<Feature> THIN_SPRUCE = key("thin_spruce");
    public static final ResourceKey<Feature> THIN_PINE = key("thin_pine");
    public static final ResourceKey<Feature> THIN_JUNGLE_TREE = key("thin_jungle_tree");
    public static final ResourceKey<Feature> THIN_ACACIA = key("thin_acacia");
    public static final ResourceKey<Feature> THIN_DARK_OAK = key("thin_dark_oak");
    public static final ResourceKey<Feature> THIN_PALE_OAK = key("thin_pale_oak");
    public static final ResourceKey<Feature> THIN_FANCY_OAK = key("thin_fancy_oak");
    public static final ResourceKey<Feature> THIN_SUPER_BIRCH = key("thin_super_birch");
    public static final ResourceKey<Feature> THIN_SWAMP_OAK = key("thin_swamp_oak");
    public static final ResourceKey<Feature> THIN_CHERRY = key("thin_cherry");
    public static final ResourceKey<Feature> THIN_MANGROVE = key("thin_mangrove");
    public static final ResourceKey<Feature> THIN_TALL_MANGROVE = key("thin_tall_mangrove");
    public static final ResourceKey<Feature> THIN_CRIMSON_FUNGUS = key("thin_crimson_fungus");
    public static final ResourceKey<Feature> THIN_WARPED_FUNGUS = key("thin_warped_fungus");
    private static final Holder<BlockStateProvider> DIRT_BELOW_TRUNK = Holder.direct(RuleBasedStateProvider.ifTrueThenProvide(
            BlockPredicate.not(BlockPredicate.matchesTag(BlockTags.CANNOT_REPLACE_BELOW_TREE_TRUNK)),
            Blocks.DIRT
    ));

    private ModTreeConfiguredFeatures() {
    }

    public static void bootstrap(BootstrapContext<Feature> context) {
        register(context, THIN_RED_POPLAR, poplar(Blocks.RED_POPLAR_LEAVES));
        register(context, THIN_ORANGE_POPLAR, poplar(Blocks.ORANGE_POPLAR_LEAVES));
        register(context, THIN_YELLOW_POPLAR, poplar(Blocks.YELLOW_POPLAR_LEAVES));
        HolderGetter<net.minecraft.world.level.block.Block> blocks = context.lookup(Registries.BLOCK);

        register(context, THIN_OAK, new ThinTreeFeature(new TreeFeature.Builder(
                BlockStateProvider.of(ModBlocks.OAK.thinBlock().get()),
                new StraightTrunkPlacer(4, 2, 0),
                leafProvider(Blocks.OAK_LEAVES),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1),
                DIRT_BELOW_TRUNK
        ).ignoreVines().build()));

        register(context, THIN_BIRCH, new ThinTreeFeature(new TreeFeature.Builder(
                BlockStateProvider.of(ModBlocks.BIRCH.thinBlock().get()),
                new StraightTrunkPlacer(5, 2, 0),
                leafProvider(Blocks.BIRCH_LEAVES),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1),
                DIRT_BELOW_TRUNK
        ).ignoreVines().build()));

        register(context, THIN_SPRUCE, new ThinTreeFeature(new TreeFeature.Builder(
                BlockStateProvider.of(ModBlocks.SPRUCE.thinBlock().get()),
                new StraightTrunkPlacer(5, 2, 1),
                leafProvider(Blocks.SPRUCE_LEAVES),
                new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(1, 2)),
                new TwoLayersFeatureSize(2, 0, 2),
                DIRT_BELOW_TRUNK
        ).ignoreVines().build()));

        register(context, THIN_PINE, new ThinTreeFeature(new TreeFeature.Builder(
                BlockStateProvider.of(ModBlocks.SPRUCE.thinBlock().get()),
                new StraightTrunkPlacer(6, 4, 0),
                leafProvider(Blocks.SPRUCE_LEAVES),
                new PineFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), UniformInt.of(3, 4)),
                new TwoLayersFeatureSize(2, 0, 2),
                DIRT_BELOW_TRUNK
        ).ignoreVines().build()));

        register(context, THIN_JUNGLE_TREE, new ThinTreeFeature(new TreeFeature.Builder(
                BlockStateProvider.of(ModBlocks.JUNGLE.thinBlock().get()),
                new StraightTrunkPlacer(4, 8, 0),
                leafProvider(Blocks.JUNGLE_LEAVES),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1),
                DIRT_BELOW_TRUNK
        ).ignoreVines().build()));

        register(context, THIN_ACACIA, new ThinTreeFeature(new TreeFeature.Builder(
                BlockStateProvider.of(ModBlocks.ACACIA.thinBlock().get()),
                new ConnectedForkingTrunkPlacer(5, 2, 2),
                leafProvider(Blocks.ACACIA_LEAVES),
                new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 0, 2),
                DIRT_BELOW_TRUNK
        ).ignoreVines().build()));

        register(context, THIN_DARK_OAK, new ThinTreeFeature(new TreeFeature.Builder(
                BlockStateProvider.of(ModBlocks.DARK_OAK.thinBlock().get()),
                new StraightTrunkPlacer(6, 2, 1),
                leafProvider(Blocks.DARK_OAK_LEAVES),
                new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
                new ThreeLayersFeatureSize(1, 0, 1, 1, 2, OptionalInt.empty()),
                DIRT_BELOW_TRUNK
        ).ignoreVines().build()));

        register(context, THIN_PALE_OAK, new ThinTreeFeature(new TreeFeature.Builder(
                BlockStateProvider.of(ModBlocks.PALE_OAK.thinBlock().get()),
                new StraightTrunkPlacer(6, 2, 1),
                leafProvider(Blocks.PALE_OAK_LEAVES),
                new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
                new ThreeLayersFeatureSize(1, 0, 1, 1, 2, OptionalInt.empty()),
                DIRT_BELOW_TRUNK
        ).ignoreVines().build()));

        register(context, THIN_FANCY_OAK, new ThinTreeFeature(new TreeFeature.Builder(
                BlockStateProvider.of(ModBlocks.OAK.thinBlock().get()),
                new ConnectedBranchingTrunkPlacer(4, 7, 0, UniformInt.of(2, 4), 0.65F, UniformInt.of(2, 4)),
                leafProvider(Blocks.OAK_LEAVES),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)),
                DIRT_BELOW_TRUNK
        ).ignoreVines().build()));

        register(context, THIN_SUPER_BIRCH, new ThinTreeFeature(new TreeFeature.Builder(
                BlockStateProvider.of(ModBlocks.BIRCH.thinBlock().get()),
                new StraightTrunkPlacer(5, 2, 6),
                leafProvider(Blocks.BIRCH_LEAVES),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1),
                DIRT_BELOW_TRUNK
        ).ignoreVines().build()));

        register(context, THIN_SWAMP_OAK, new ThinTreeFeature(new TreeFeature.Builder(
                BlockStateProvider.of(ModBlocks.OAK.thinBlock().get()),
                new StraightTrunkPlacer(5, 3, 0),
                leafProvider(Blocks.OAK_LEAVES),
                new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1),
                DIRT_BELOW_TRUNK
        ).build()));

        register(context, THIN_CHERRY, new ThinTreeFeature(new TreeFeature.Builder(
                BlockStateProvider.of(ModBlocks.CHERRY.thinBlock().get()),
                new CherryTrunkPlacer(
                        7,
                        1,
                        0,
                        ConstantInt.of(3),
                        UniformInt.of(2, 4),
                        UniformInt.of(-4, -3),
                        UniformInt.of(-1, 0)
                ),
                leafProvider(Blocks.CHERRY_LEAVES),
                new CherryFoliagePlacer(
                        ConstantInt.of(4),
                        ConstantInt.of(0),
                        ConstantInt.of(5),
                        0.25F,
                        0.25F,
                        0.16666667F,
                        0.33333334F
                ),
                new TwoLayersFeatureSize(1, 0, 2),
                DIRT_BELOW_TRUNK
        ).ignoreVines().build()));

        register(context, THIN_MANGROVE, new ThinTreeFeature(new TreeFeature.Builder(
                BlockStateProvider.of(ModBlocks.MANGROVE.thinBlock().get()),
                new ConnectedBranchingTrunkPlacer(3, 1, 4, UniformInt.of(2, 4), 0.55F, UniformInt.of(2, 3)),
                leafProvider(Blocks.MANGROVE_LEAVES),
                new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 70),
                Optional.of(mangroveRootPlacer(blocks, UniformInt.of(1, 3))),
                new TwoLayersFeatureSize(2, 0, 2),
                DIRT_BELOW_TRUNK
        ).ignoreVines().build()));

        register(context, THIN_TALL_MANGROVE, new ThinTreeFeature(new TreeFeature.Builder(
                BlockStateProvider.of(ModBlocks.MANGROVE.thinBlock().get()),
                new ConnectedBranchingTrunkPlacer(5, 1, 7, UniformInt.of(3, 6), 0.6F, UniformInt.of(2, 4)),
                leafProvider(Blocks.MANGROVE_LEAVES),
                new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 70),
                Optional.of(mangroveRootPlacer(blocks, UniformInt.of(3, 7))),
                new TwoLayersFeatureSize(3, 0, 2),
                DIRT_BELOW_TRUNK
        ).ignoreVines().build()));

        register(context, THIN_CRIMSON_FUNGUS, new HugeFungusFeature(
                Blocks.CRIMSON_NYLIUM.defaultBlockState(),
                ModBlocks.CRIMSON.thinBlock().get().defaultBlockState(),
                Blocks.NETHER_WART_BLOCK.defaultBlockState(),
                Blocks.SHROOMLIGHT.defaultBlockState(),
                replaceableBlocksPredicate(),
                false
        ));

        register(context, THIN_WARPED_FUNGUS, new HugeFungusFeature(
                Blocks.WARPED_NYLIUM.defaultBlockState(),
                ModBlocks.WARPED.thinBlock().get().defaultBlockState(),
                Blocks.WARPED_WART_BLOCK.defaultBlockState(),
                Blocks.SHROOMLIGHT.defaultBlockState(),
                replaceableBlocksPredicate(),
                false
        ));
    }

    private static ThinTreeFeature poplar(net.minecraft.world.level.block.Block leaves) {
        var radius = net.minecraft.util.random.WeightedList.<IntProvider>builder()
                .add(ConstantInt.of(5), 5).add(ConstantInt.of(6), 5)
                .add(ConstantInt.of(7), 1).add(ConstantInt.of(8), 1).build();
        return new ThinTreeFeature(new TreeFeature.Builder(
                BlockStateProvider.of(ModBlocks.POPLAR.thinBlock().get()),
                new net.minecraft.world.level.levelgen.feature.trunkplacers.PoplarTrunkPlacer(
                        7, 4, 0, ConstantInt.of(4), UniformInt.of(1, 4)),
                leafProvider(leaves),
                new net.minecraft.world.level.levelgen.feature.foliageplacers.PoplarFoliagePlacer(
                        new net.minecraft.util.valueproviders.WeightedListInt(radius),
                        ConstantInt.of(0), UniformInt.of(5, 6), 0.15F),
                new TwoLayersFeatureSize(1, 0, 2), DIRT_BELOW_TRUNK
        ).ignoreVines().build());
    }

    private static MangroveRootPlacer mangroveRootPlacer(HolderGetter<net.minecraft.world.level.block.Block> blocks, IntProvider trunkOffset) {
        return new MangroveRootPlacer(
                trunkOffset,
                Holder.direct(BlockStateProvider.of(Blocks.MANGROVE_ROOTS)),
                Optional.of(new AboveRootPlacement(Holder.direct(BlockStateProvider.of(Blocks.MOSS_CARPET)), 0.5F)),
                new MangroveRootPlacement(
                        blocks.getOrThrow(BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH),
                        HolderSet.direct(net.minecraft.world.level.block.Block::builtInRegistryHolder, Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS),
                        Holder.direct(BlockStateProvider.of(Blocks.MUDDY_MANGROVE_ROOTS)),
                        8,
                        15,
                        0.2F
                )
        );
    }

    private static net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate replaceableBlocksPredicate() {
        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.matchesBlocks(
                Blocks.OAK_SAPLING,
                Blocks.SPRUCE_SAPLING,
                Blocks.BIRCH_SAPLING,
                Blocks.POPLAR_SAPLING,
                Blocks.JUNGLE_SAPLING,
                Blocks.ACACIA_SAPLING,
                Blocks.CHERRY_SAPLING,
                Blocks.DARK_OAK_SAPLING,
                Blocks.PALE_OAK_SAPLING,
                Blocks.MANGROVE_PROPAGULE,
                Blocks.DANDELION,
                Blocks.TORCHFLOWER,
                Blocks.POPPY,
                Blocks.BLUE_ORCHID,
                Blocks.ALLIUM,
                Blocks.AZURE_BLUET,
                Blocks.RED_TULIP,
                Blocks.ORANGE_TULIP,
                Blocks.WHITE_TULIP,
                Blocks.PINK_TULIP,
                Blocks.OXEYE_DAISY,
                Blocks.CORNFLOWER,
                Blocks.WITHER_ROSE,
                Blocks.LILY_OF_THE_VALLEY,
                Blocks.BROWN_MUSHROOM,
                Blocks.RED_MUSHROOM,
                Blocks.WHEAT,
                Blocks.SUGAR_CANE,
                Blocks.ATTACHED_PUMPKIN_STEM,
                Blocks.ATTACHED_MELON_STEM,
                Blocks.PUMPKIN_STEM,
                Blocks.MELON_STEM,
                Blocks.LILY_PAD,
                Blocks.NETHER_WART,
                Blocks.COCOA,
                Blocks.CARROTS,
                Blocks.POTATOES,
                Blocks.CHORUS_PLANT,
                Blocks.CHORUS_FLOWER,
                Blocks.TORCHFLOWER_CROP,
                Blocks.PITCHER_CROP,
                Blocks.BEETROOTS,
                Blocks.SWEET_BERRY_BUSH,
                Blocks.WARPED_FUNGUS,
                Blocks.CRIMSON_FUNGUS,
                Blocks.WEEPING_VINES,
                Blocks.WEEPING_VINES_PLANT,
                Blocks.TWISTING_VINES,
                Blocks.TWISTING_VINES_PLANT,
                Blocks.CAVE_VINES,
                Blocks.CAVE_VINES_PLANT,
                Blocks.SPORE_BLOSSOM,
                Blocks.AZALEA,
                Blocks.FLOWERING_AZALEA,
                Blocks.MOSS_CARPET,
                Blocks.PALE_MOSS_CARPET,
                Blocks.PALE_HANGING_MOSS,
                Blocks.PINK_PETALS,
                Blocks.BIG_DRIPLEAF,
                Blocks.BIG_DRIPLEAF_STEM,
                Blocks.SMALL_DRIPLEAF
        );
    }

    private static BlockStateProvider leafProvider(net.minecraft.world.level.block.Block leaves) {
        return BlockStateProvider.of(leaves.defaultBlockState()
                .setValue(net.minecraft.world.level.block.LeavesBlock.DISTANCE, 7)
                .setValue(net.minecraft.world.level.block.LeavesBlock.PERSISTENT, false)
                .setValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED, false));
    }

    private static ResourceKey<Feature> key(String name) {
        return ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath(ThinLogs.MOD_ID, name));
    }

    private static void register(BootstrapContext<Feature> context, ResourceKey<Feature> key, Feature feature) {
        context.register(key, feature);
    }
}
