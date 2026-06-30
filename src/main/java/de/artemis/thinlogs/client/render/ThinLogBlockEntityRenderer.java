package de.artemis.thinlogs.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import de.artemis.thinlogs.common.blocks.ThinLogBlockEntity;
import de.artemis.thinlogs.common.blocks.ThinLogOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class ThinLogBlockEntityRenderer implements BlockEntityRenderer<ThinLogBlockEntity> {
    public ThinLogBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(ThinLogBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getLevel() == null) {
            return;
        }

        renderOverlay(blockEntity.getSurfaceOverlayState(), poseStack, bufferSource, packedLight, packedOverlay);
    }

    private void renderOverlay(BlockState overlayState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (overlayState == null || ThinLogOverlay.type(overlayState) == ThinLogOverlay.OverlayType.NONE) {
            return;
        }

        poseStack.pushPose();
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(overlayState, poseStack, bufferSource, packedLight, packedOverlay);
        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(ThinLogBlockEntity blockEntity) {
        return false;
    }
}
