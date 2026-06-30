package de.artemis.thinlogs.common.worldgen.feature;

import com.mojang.serialization.Codec;
import de.artemis.thinlogs.common.blockStateProperties.AnchorFace;
import de.artemis.thinlogs.common.blockStateProperties.CoreOrientation;
import de.artemis.thinlogs.common.blockStateProperties.ModBlockStateProperties;
import de.artemis.thinlogs.common.blocks.ThinLogBlock;
import de.artemis.thinlogs.common.blocks.ThinLogBlockEntity;
import de.artemis.thinlogs.common.blocks.ThinLogOverlay;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThinTreeFeature extends Feature<TreeConfiguration> {
    private static final int SCAN_RADIUS_XZ = 8;
    private static final int SCAN_BELOW = 2;
    private static final int SCAN_ABOVE = 24;

    public ThinTreeFeature(Codec<TreeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<TreeConfiguration> context) {
        Map<BlockPos, BlockState> originalStates = snapshotArea(context.level(), context.origin());
        boolean placed = Feature.TREE.place(
                context.config(),
                context.level(),
                context.chunkGenerator(),
                context.random(),
                context.origin()
        );
        if (!placed) {
            return false;
        }

        postProcess(context.level(), context.origin(), context.config(), originalStates);
        return true;
    }

    private static void postProcess(WorldGenLevel level, BlockPos origin, TreeConfiguration configuration, Map<BlockPos, BlockState> originalStates) {
        List<BlockPos> thinLogPositions = findThinLogs(level, origin);
        if (thinLogPositions.isEmpty()) {
            return;
        }

        BlockState leafOverlaySample = normalizeLeafOverlayState(configuration.foliageProvider.getState(RandomSource.create(0L), origin));
        restoreSupportBlocks(level, thinLogPositions, originalStates);

        for (BlockPos pos : thinLogPositions) {
            BlockState state = level.getBlockState(pos);
            BlockState anchoredState = applyGroundAnchor(state, level, pos);
            if (anchoredState != state) {
                level.setBlock(pos, anchoredState, 19);
            }
        }

        for (BlockPos pos : thinLogPositions) {
            BlockState state = level.getBlockState(pos);
            ThinLogBlockEntity blockEntity = getOrCreateBlockEntity(level, pos, state);
            if (blockEntity == null) {
                continue;
            }

            if (blockEntity.getFoliageOverlayState() == null) {
                BlockState foliageOverlay = findLeafOverlay(level, pos, leafOverlaySample);
                if (foliageOverlay != null) {
                    blockEntity.setFoliageOverlayState(foliageOverlay);
                }
            }

            if (blockEntity.getFoliageOverlayState() == null
                    && blockEntity.getSurfaceOverlayState() == null
                    && shouldAddBottomSnow(level, pos, originalStates)) {
                blockEntity.setSurfaceOverlayState(Blocks.SNOW.defaultBlockState().setValue(SnowLayerBlock.LAYERS, 1));
            }

            updateBelowSnowyState(level, pos, blockEntity.getFoliageOverlayState(), blockEntity.getSurfaceOverlayState());
            blockEntity.setChanged();
        }
    }

    private static Map<BlockPos, BlockState> snapshotArea(WorldGenLevel level, BlockPos origin) {
        Map<BlockPos, BlockState> snapshot = new HashMap<>();
        BlockPos min = origin.offset(-SCAN_RADIUS_XZ, -SCAN_BELOW, -SCAN_RADIUS_XZ);
        BlockPos max = origin.offset(SCAN_RADIUS_XZ, SCAN_ABOVE, SCAN_RADIUS_XZ);

        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            snapshot.put(pos.immutable(), level.getBlockState(pos));
        }

        return snapshot;
    }

    private static List<BlockPos> findThinLogs(WorldGenLevel level, BlockPos origin) {
        List<BlockPos> positions = new ArrayList<>();
        BlockPos min = origin.offset(-SCAN_RADIUS_XZ, -SCAN_BELOW, -SCAN_RADIUS_XZ);
        BlockPos max = origin.offset(SCAN_RADIUS_XZ, SCAN_ABOVE, SCAN_RADIUS_XZ);

        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            if (level.getBlockState(pos).getBlock() instanceof ThinLogBlock) {
                positions.add(pos.immutable());
            }
        }

        return positions;
    }

    private static BlockState applyGroundAnchor(BlockState state, WorldGenLevel level, BlockPos pos) {
        if (!(state.getBlock() instanceof ThinLogBlock)) {
            return state;
        }

        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        boolean anchorDown = !canHardConnectTo(belowState) && belowState.isFaceSturdy(level, belowPos, Direction.UP);

        BlockState updated = state.setValue(
                ModBlockStateProperties.ANCHOR_FACE,
                anchorDown ? AnchorFace.DOWN : AnchorFace.NONE
        );

        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.relative(direction);
            BlockState neighborState = level.getBlockState(neighborPos);
            boolean connected = canHardConnectTo(neighborState)
                    || (direction == Direction.DOWN && anchorDown);
            updated = updated.setValue(ModBlockStateProperties.connection(direction), connected);
        }

        return updated.setValue(ModBlockStateProperties.CORE_ORIENTATION, resolveCoreOrientation(updated));
    }

    private static boolean canHardConnectTo(BlockState state) {
        return state.getBlock() instanceof ThinLogBlock || state.is(BlockTags.LOGS);
    }

    private static CoreOrientation resolveCoreOrientation(BlockState state) {
        boolean x = state.getValue(ModBlockStateProperties.EAST) || state.getValue(ModBlockStateProperties.WEST);
        boolean y = state.getValue(ModBlockStateProperties.UP) || state.getValue(ModBlockStateProperties.DOWN);
        boolean z = state.getValue(ModBlockStateProperties.NORTH) || state.getValue(ModBlockStateProperties.SOUTH);

        if (y) {
            return CoreOrientation.VERTICAL;
        }
        if (x && z) {
            return CoreOrientation.JUNCTION;
        }
        if (x) {
            return CoreOrientation.EAST_WEST;
        }
        if (z) {
            return CoreOrientation.NORTH_SOUTH;
        }
        return CoreOrientation.VERTICAL;
    }

    private static BlockState normalizeLeafOverlayState(BlockState state) {
        if (state.hasProperty(LeavesBlock.PERSISTENT)) {
            state = state.setValue(LeavesBlock.PERSISTENT, true);
        }
        if (state.hasProperty(LeavesBlock.DISTANCE)) {
            state = state.setValue(LeavesBlock.DISTANCE, 1);
        }
        if (state.hasProperty(BlockStateProperties.WATERLOGGED)) {
            state = state.setValue(BlockStateProperties.WATERLOGGED, false);
        }
        return state;
    }

    private static BlockState findLeafOverlay(WorldGenLevel level, BlockPos pos, BlockState fallbackLeafState) {
        for (Direction direction : Direction.values()) {
            BlockState neighborState = level.getBlockState(pos.relative(direction));
            if (neighborState.is(BlockTags.LEAVES)) {
                return normalizeLeafOverlayState(neighborState);
            }
        }
        return hasLeafNeighbor(level, pos) ? fallbackLeafState : null;
    }

    private static boolean hasLeafNeighbor(WorldGenLevel level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (level.getBlockState(pos.relative(direction)).is(BlockTags.LEAVES)) {
                return true;
            }
        }
        return false;
    }

    private static boolean shouldAddBottomSnow(WorldGenLevel level, BlockPos pos, Map<BlockPos, BlockState> originalStates) {
        if (level.getBlockState(pos.below()).getBlock() instanceof ThinLogBlock) {
            return false;
        }

        BlockState originalAtPos = originalStates.get(pos);
        if (isSnowSupportState(originalAtPos)) {
            return true;
        }

        BlockState originalBelow = originalStates.get(pos.below());
        if (isSnowSupportState(originalBelow)) {
            return true;
        }

        BlockState belowState = level.getBlockState(pos.below());
        return isSnowSupportState(belowState);
    }

    private static boolean isSnowSupportState(BlockState state) {
        return state != null && (state.is(Blocks.SNOW_BLOCK) || state.is(Blocks.POWDER_SNOW) || state.is(Blocks.SNOW));
    }

    private static void restoreSupportBlocks(WorldGenLevel level, List<BlockPos> thinLogPositions, Map<BlockPos, BlockState> originalStates) {
        for (BlockPos pos : thinLogPositions) {
            BlockPos belowPos = pos.below();
            BlockState originalState = originalStates.get(belowPos);
            if (originalState == null || originalState.isAir()) {
                continue;
            }

            BlockState currentState = level.getBlockState(belowPos);
            if (!currentState.is(Blocks.DIRT)) {
                continue;
            }
            if (currentState == originalState) {
                continue;
            }

            level.setBlock(belowPos, originalState, 19);
        }
    }

    private static void updateBelowSnowyState(WorldGenLevel level, BlockPos pos, BlockState foliageOverlayState, BlockState surfaceOverlayState) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        if (!belowState.hasProperty(SnowyDirtBlock.SNOWY)) {
            return;
        }

        boolean snowy = foliageOverlayState == null && ThinLogOverlay.type(surfaceOverlayState) == ThinLogOverlay.OverlayType.SNOW;
        if (belowState.getValue(SnowyDirtBlock.SNOWY) != snowy) {
            level.setBlock(belowPos, belowState.setValue(SnowyDirtBlock.SNOWY, snowy), 19);
        }
    }

    private static ThinLogBlockEntity getOrCreateBlockEntity(WorldGenLevel level, BlockPos pos, BlockState state) {
        BlockEntity existing = level.getBlockEntity(pos);
        if (existing instanceof ThinLogBlockEntity thinLogBlockEntity) {
            return thinLogBlockEntity;
        }

        if (!(state.getBlock() instanceof EntityBlock entityBlock)) {
            return null;
        }

        BlockEntity created = entityBlock.newBlockEntity(pos, state);
        if (!(created instanceof ThinLogBlockEntity thinLogBlockEntity)) {
            return null;
        }

        level.getChunk(pos).setBlockEntity(thinLogBlockEntity);
        return thinLogBlockEntity;
    }
}
