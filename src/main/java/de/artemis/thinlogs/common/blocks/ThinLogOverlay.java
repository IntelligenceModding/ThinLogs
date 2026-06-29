package de.artemis.thinlogs.common.blocks;

import de.artemis.thinlogs.common.registration.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
        if (state.is(ModTags.BlockTagsSet.THIN_LOG_OVERLAY_LEAVES)) {
            return OverlayType.LEAVES;
        }
        if (state.is(ModTags.BlockTagsSet.THIN_LOG_OVERLAY_CARPETS)) {
            return OverlayType.CARPET;
        }
        if (state.is(ModTags.BlockTagsSet.THIN_LOG_OVERLAY_SNOW)) {
            return OverlayType.SNOW;
        }
        return OverlayType.NONE;
    }

    public static boolean canApply(@Nullable BlockState currentOverlay, BlockState candidate) {
        OverlayType candidateType = type(candidate);
        if (candidateType == OverlayType.NONE) {
            return false;
        }

        if (candidateType == OverlayType.SNOW) {
            if (currentOverlay == null) {
                return true;
            }
            if (type(currentOverlay) != OverlayType.SNOW) {
                return false;
            }
            return snowLayers(currentOverlay) < 8;
        }

        return currentOverlay == null;
    }

    public static BlockState nextOverlayState(@Nullable BlockState currentOverlay, BlockState candidate) {
        OverlayType candidateType = type(candidate);
        if (candidateType == OverlayType.SNOW) {
            int candidateLayers = candidate.is(Blocks.SNOW_BLOCK) ? 8 : 1;
            int currentLayers = currentOverlay != null && type(currentOverlay) == OverlayType.SNOW ? snowLayers(currentOverlay) : 0;
            int resultingLayers = Math.min(8, currentLayers + candidateLayers);
            return Blocks.SNOW.defaultBlockState().setValue(SnowLayerBlock.LAYERS, resultingLayers);
        }
        return candidate;
    }

    public static ItemStack dropStack(BlockState overlayState) {
        if (type(overlayState) == OverlayType.SNOW) {
            return new ItemStack(Blocks.SNOW, snowLayers(overlayState));
        }
        return new ItemStack(overlayState.getBlock());
    }

    public static void drop(Level level, BlockPos pos, @Nullable BlockState overlayState) {
        if (overlayState == null || level.isClientSide) {
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
