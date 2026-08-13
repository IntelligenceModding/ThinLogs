package de.artemis.thinlogs.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public final class ThinLogOverlay {
    private ThinLogOverlay() {
    }

    public static OverlayType type(@Nullable BlockState state) {
        if (state == null) {
            return OverlayType.NONE;
        }
        if (state.getBlock() instanceof LeavesBlock || state.is(BlockTags.LEAVES)) {
            return OverlayType.LEAVES;
        }
        if (state.getBlock() instanceof CarpetBlock || state.is(Blocks.MOSS_CARPET)) {
            return OverlayType.CARPET;
        }
        if (state.is(Blocks.SNOW) || state.is(Blocks.SNOW_BLOCK)) {
            return OverlayType.SNOW;
        }
        return OverlayType.NONE;
    }

    public static boolean canApplyFoliage(@Nullable BlockState currentFoliageOverlay, @Nullable BlockState currentSurfaceOverlay, BlockState candidate) {
        OverlayType candidateType = type(candidate);
        return candidateType == OverlayType.LEAVES && currentFoliageOverlay == null && currentSurfaceOverlay == null;
    }

    public static boolean canApplySurface(@Nullable BlockState currentSurfaceOverlay, BlockState candidate, Level level, BlockPos pos, @Nullable BlockState supportState) {
        OverlayType candidateType = type(candidate);
        if (candidateType != OverlayType.CARPET && candidateType != OverlayType.SNOW) {
            return false;
        }
        if (supportState != null) {
            return false;
        }
        if (!canSurfaceSurviveAt(candidate, level, pos, supportState)) {
            return false;
        }

        if (candidateType == OverlayType.SNOW) {
            if (currentSurfaceOverlay == null) {
                return true;
            }
            if (type(currentSurfaceOverlay) != OverlayType.SNOW) {
                return false;
            }
            return snowLayers(currentSurfaceOverlay) < 8;
        }

        return currentSurfaceOverlay == null;
    }

    public static boolean canSurviveAt(@Nullable BlockState overlayState, LevelReader level, BlockPos pos) {
        if (overlayState == null) {
            return true;
        }

        return switch (type(overlayState)) {
            case NONE, LEAVES -> true;
            case CARPET, SNOW -> canSurfaceSurviveAt(overlayState, level, pos, null);
        };
    }

    public static boolean canSurfaceSurviveAt(@Nullable BlockState overlayState, LevelReader level, BlockPos pos, @Nullable BlockState supportState) {
        if (overlayState == null) {
            return true;
        }

        return switch (type(overlayState)) {
            case NONE, LEAVES -> true;
            case CARPET -> canSupportCarpet(level, pos, supportState);
            case SNOW -> canSupportSnow(level, pos, supportState);
        };
    }

    @Nullable
    public static BlockState nextSurfaceState(@Nullable BlockState currentSurfaceOverlay, BlockState candidate) {
        OverlayType candidateType = type(candidate);
        if (candidateType == OverlayType.SNOW) {
            int candidateLayers = candidate.is(Blocks.SNOW_BLOCK) ? 8 : 1;
            int currentLayers = currentSurfaceOverlay != null && type(currentSurfaceOverlay) == OverlayType.SNOW ? snowLayers(currentSurfaceOverlay) : 0;
            int resultingLayers = Math.min(8, currentLayers + candidateLayers);
            return Blocks.SNOW.defaultBlockState().setValue(SnowLayerBlock.LAYERS, resultingLayers);
        }
        return candidate;
    }

    private static boolean canSupportCarpet(LevelReader level, BlockPos pos, @Nullable BlockState supportState) {
        if (supportState != null) {
            return !supportState.isAir();
        }

        BlockState belowState = level.getBlockState(pos.below());
        return !belowState.isAir() && !(belowState.getBlock() instanceof ThinLogBlock);
    }

    private static boolean canSupportSnow(LevelReader level, BlockPos pos, @Nullable BlockState supportState) {
        BlockState resolvedSupportState = supportState != null ? supportState : level.getBlockState(pos.below());
        BlockPos supportPos = supportState != null ? pos : pos.below();
        if (resolvedSupportState.is(BlockTags.CANNOT_SUPPORT_SNOW_LAYER)) {
            return false;
        }
        if (resolvedSupportState.is(BlockTags.SUPPORT_OVERRIDE_SNOW_LAYER)) {
            return true;
        }
        return Block.isFaceFull(resolvedSupportState.getCollisionShape(level, supportPos), net.minecraft.core.Direction.UP)
                || (resolvedSupportState.is(Blocks.SNOW)
                && resolvedSupportState.hasProperty(SnowLayerBlock.LAYERS)
                && resolvedSupportState.getValue(SnowLayerBlock.LAYERS) == 8);
    }

    public static ItemStack dropStack(BlockState overlayState) {
        if (type(overlayState) == OverlayType.SNOW) {
            return new ItemStack(Blocks.SNOW, snowLayers(overlayState));
        }
        return new ItemStack(overlayState.getBlock());
    }

    public static void drop(Level level, BlockPos pos, @Nullable BlockState overlayState) {
        if (overlayState == null || level.isClientSide()) {
            return;
        }
        Block.popResource(level, pos, dropStack(overlayState));
    }

    public static int snowLayers(BlockState state) {
        if (state.is(Blocks.SNOW_BLOCK)) {
            return 8;
        }
        if (state.is(Blocks.SNOW) && state.hasProperty(SnowLayerBlock.LAYERS)) {
            return state.getValue(SnowLayerBlock.LAYERS);
        }
        return 1;
    }

    public enum OverlayType {
        NONE,
        LEAVES,
        CARPET,
        SNOW
    }
}
