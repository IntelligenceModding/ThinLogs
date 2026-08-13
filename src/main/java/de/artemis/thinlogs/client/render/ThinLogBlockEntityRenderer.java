package de.artemis.thinlogs.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import de.artemis.thinlogs.common.blocks.ThinLogBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class ThinLogBlockEntityRenderer implements BlockEntityRenderer<ThinLogBlockEntity, ThinLogBlockEntityRenderer.ThinLogRenderState> {
    public ThinLogBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public ThinLogRenderState createRenderState() {
        return new ThinLogRenderState();
    }

    @Override
    public void extractRenderState(
            ThinLogBlockEntity blockEntity,
            ThinLogRenderState renderState,
            float partialTick,
            Vec3 cameraPosition,
            ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress
    ) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);
        renderState.surfaceOverlayState = blockEntity.getSurfaceOverlayState();
    }

    @Override
    public void submit(ThinLogRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
    }

    @Override
    public boolean shouldRenderOffScreen() {
        return false;
    }

    public static class ThinLogRenderState extends BlockEntityRenderState {
        @Nullable
        public BlockState surfaceOverlayState;
    }
}
