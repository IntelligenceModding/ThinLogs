package de.artemis.thinlogs.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.artemis.thinlogs.common.blocks.ThinLogBlockEntity;
import de.artemis.thinlogs.common.blocks.ThinLogGeometry;
import de.artemis.thinlogs.common.blocks.ThinLogOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;

public class ThinLogBlockEntityRenderer implements BlockEntityRenderer<ThinLogBlockEntity> {
    private static final float PIXEL = 1.0F / 16.0F;
    private static final float LEAF_INFLATION = 0.45F;
    private static final float SURFACE_EPSILON = 0.02F;

    public ThinLogBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(ThinLogBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState overlayState = blockEntity.getOverlayState();
        if (overlayState == null || blockEntity.getLevel() == null) {
            return;
        }

        ThinLogOverlay.OverlayType overlayType = ThinLogOverlay.type(overlayState);
        if (overlayType == ThinLogOverlay.OverlayType.NONE) {
            return;
        }

        BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(overlayState);
        TextureAtlasSprite sprite = model.getParticleIcon();
        int tint = Minecraft.getInstance().getBlockColors().getColor(overlayState, blockEntity.getLevel(), blockEntity.getBlockPos(), 0);
        if (tint == -1) {
            tint = 0xFFFFFFFF;
        }

        VertexConsumer consumer = bufferSource.getBuffer(Sheets.cutoutBlockSheet());
        PoseStack.Pose pose = poseStack.last();

        switch (overlayType) {
            case LEAVES -> renderLeaves(blockEntity, consumer, pose, sprite, tint, packedLight, packedOverlay);
            case CARPET -> renderSurfaceOverlay(blockEntity, consumer, pose, sprite, tint, packedLight, packedOverlay, 1.0F);
            case SNOW -> renderSurfaceOverlay(blockEntity, consumer, pose, sprite, tint, packedLight, packedOverlay, snowHeight(overlayState));
            default -> {
            }
        }
    }

    private void renderLeaves(ThinLogBlockEntity blockEntity, VertexConsumer consumer, PoseStack.Pose pose, TextureAtlasSprite sprite, int tint, int packedLight, int packedOverlay) {
        for (ThinLogGeometry.Cuboid cuboid : ThinLogGeometry.cuboids(blockEntity.getBlockState())) {
            renderCuboid(consumer, pose, sprite, tint, packedLight, packedOverlay,
                    cuboid.minX() - LEAF_INFLATION,
                    cuboid.minY() - LEAF_INFLATION,
                    cuboid.minZ() - LEAF_INFLATION,
                    cuboid.maxX() + LEAF_INFLATION,
                    cuboid.maxY() + LEAF_INFLATION,
                    cuboid.maxZ() + LEAF_INFLATION,
                    true);
        }
    }

    private void renderSurfaceOverlay(ThinLogBlockEntity blockEntity, VertexConsumer consumer, PoseStack.Pose pose, TextureAtlasSprite sprite, int tint, int packedLight, int packedOverlay, float heightPixels) {
        for (ThinLogGeometry.Cuboid cuboid : ThinLogGeometry.cuboids(blockEntity.getBlockState())) {
            float minX = cuboid.minX();
            float minZ = cuboid.minZ();
            float maxX = cuboid.maxX();
            float maxZ = cuboid.maxZ();
            float minY = cuboid.maxY() - SURFACE_EPSILON;
            float maxY = Math.min(16.0F, cuboid.maxY() + heightPixels);
            renderCuboid(consumer, pose, sprite, tint, packedLight, packedOverlay, minX, minY, minZ, maxX, maxY, maxZ, false);
        }
    }

    private void renderCuboid(VertexConsumer consumer, PoseStack.Pose pose, TextureAtlasSprite sprite, int tint, int packedLight, int packedOverlay,
                              float minX, float minY, float minZ, float maxX, float maxY, float maxZ, boolean renderAllFaces) {
        float x0 = minX * PIXEL;
        float y0 = minY * PIXEL;
        float z0 = minZ * PIXEL;
        float x1 = maxX * PIXEL;
        float y1 = maxY * PIXEL;
        float z1 = maxZ * PIXEL;

        int red = FastColor.ARGB32.red(tint);
        int green = FastColor.ARGB32.green(tint);
        int blue = FastColor.ARGB32.blue(tint);
        int alpha = FastColor.ARGB32.alpha(tint);
        if (alpha == 0) {
            alpha = 255;
        }

        putFace(consumer, pose, sprite, packedLight, packedOverlay, red, green, blue, alpha, Direction.UP, x0, y1, z0, x1, y1, z1);
        if (renderAllFaces) {
            putFace(consumer, pose, sprite, packedLight, packedOverlay, red, green, blue, alpha, Direction.DOWN, x0, y0, z0, x1, y0, z1);
            putFace(consumer, pose, sprite, packedLight, packedOverlay, red, green, blue, alpha, Direction.NORTH, x0, y0, z0, x1, y1, z0);
            putFace(consumer, pose, sprite, packedLight, packedOverlay, red, green, blue, alpha, Direction.SOUTH, x0, y0, z1, x1, y1, z1);
            putFace(consumer, pose, sprite, packedLight, packedOverlay, red, green, blue, alpha, Direction.WEST, x0, y0, z0, x0, y1, z1);
            putFace(consumer, pose, sprite, packedLight, packedOverlay, red, green, blue, alpha, Direction.EAST, x1, y0, z0, x1, y1, z1);
        } else {
            putFace(consumer, pose, sprite, packedLight, packedOverlay, red, green, blue, alpha, Direction.NORTH, x0, y0, z0, x1, y1, z0);
            putFace(consumer, pose, sprite, packedLight, packedOverlay, red, green, blue, alpha, Direction.SOUTH, x0, y0, z1, x1, y1, z1);
            putFace(consumer, pose, sprite, packedLight, packedOverlay, red, green, blue, alpha, Direction.WEST, x0, y0, z0, x0, y1, z1);
            putFace(consumer, pose, sprite, packedLight, packedOverlay, red, green, blue, alpha, Direction.EAST, x1, y0, z0, x1, y1, z1);
        }
    }

    private void putFace(VertexConsumer consumer, PoseStack.Pose pose, TextureAtlasSprite sprite, int packedLight, int packedOverlay,
                         int red, int green, int blue, int alpha, Direction direction,
                         float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        float u0 = sprite.getU0();
        float v0 = sprite.getV0();
        float u1 = sprite.getU1();
        float v1 = sprite.getV1();

        switch (direction) {
            case UP -> {
                vertex(consumer, pose, minX, minY, minZ, u0, v0, packedLight, packedOverlay, red, green, blue, alpha, 0.0F, 1.0F, 0.0F);
                vertex(consumer, pose, minX, minY, maxZ, u0, v1, packedLight, packedOverlay, red, green, blue, alpha, 0.0F, 1.0F, 0.0F);
                vertex(consumer, pose, maxX, minY, maxZ, u1, v1, packedLight, packedOverlay, red, green, blue, alpha, 0.0F, 1.0F, 0.0F);
                vertex(consumer, pose, maxX, minY, minZ, u1, v0, packedLight, packedOverlay, red, green, blue, alpha, 0.0F, 1.0F, 0.0F);
            }
            case DOWN -> {
                vertex(consumer, pose, minX, minY, minZ, u0, v0, packedLight, packedOverlay, red, green, blue, alpha, 0.0F, -1.0F, 0.0F);
                vertex(consumer, pose, maxX, minY, minZ, u1, v0, packedLight, packedOverlay, red, green, blue, alpha, 0.0F, -1.0F, 0.0F);
                vertex(consumer, pose, maxX, minY, maxZ, u1, v1, packedLight, packedOverlay, red, green, blue, alpha, 0.0F, -1.0F, 0.0F);
                vertex(consumer, pose, minX, minY, maxZ, u0, v1, packedLight, packedOverlay, red, green, blue, alpha, 0.0F, -1.0F, 0.0F);
            }
            case NORTH -> {
                vertex(consumer, pose, maxX, minY, minZ, u1, v1, packedLight, packedOverlay, red, green, blue, alpha, 0.0F, 0.0F, -1.0F);
                vertex(consumer, pose, maxX, maxY, minZ, u1, v0, packedLight, packedOverlay, red, green, blue, alpha, 0.0F, 0.0F, -1.0F);
                vertex(consumer, pose, minX, maxY, minZ, u0, v0, packedLight, packedOverlay, red, green, blue, alpha, 0.0F, 0.0F, -1.0F);
                vertex(consumer, pose, minX, minY, minZ, u0, v1, packedLight, packedOverlay, red, green, blue, alpha, 0.0F, 0.0F, -1.0F);
            }
            case SOUTH -> {
                vertex(consumer, pose, minX, minY, maxZ, u0, v1, packedLight, packedOverlay, red, green, blue, alpha, 0.0F, 0.0F, 1.0F);
                vertex(consumer, pose, minX, maxY, maxZ, u0, v0, packedLight, packedOverlay, red, green, blue, alpha, 0.0F, 0.0F, 1.0F);
                vertex(consumer, pose, maxX, maxY, maxZ, u1, v0, packedLight, packedOverlay, red, green, blue, alpha, 0.0F, 0.0F, 1.0F);
                vertex(consumer, pose, maxX, minY, maxZ, u1, v1, packedLight, packedOverlay, red, green, blue, alpha, 0.0F, 0.0F, 1.0F);
            }
            case WEST -> {
                vertex(consumer, pose, minX, minY, minZ, u1, v1, packedLight, packedOverlay, red, green, blue, alpha, -1.0F, 0.0F, 0.0F);
                vertex(consumer, pose, minX, maxY, minZ, u1, v0, packedLight, packedOverlay, red, green, blue, alpha, -1.0F, 0.0F, 0.0F);
                vertex(consumer, pose, minX, maxY, maxZ, u0, v0, packedLight, packedOverlay, red, green, blue, alpha, -1.0F, 0.0F, 0.0F);
                vertex(consumer, pose, minX, minY, maxZ, u0, v1, packedLight, packedOverlay, red, green, blue, alpha, -1.0F, 0.0F, 0.0F);
            }
            case EAST -> {
                vertex(consumer, pose, maxX, minY, maxZ, u1, v1, packedLight, packedOverlay, red, green, blue, alpha, 1.0F, 0.0F, 0.0F);
                vertex(consumer, pose, maxX, maxY, maxZ, u1, v0, packedLight, packedOverlay, red, green, blue, alpha, 1.0F, 0.0F, 0.0F);
                vertex(consumer, pose, maxX, maxY, minZ, u0, v0, packedLight, packedOverlay, red, green, blue, alpha, 1.0F, 0.0F, 0.0F);
                vertex(consumer, pose, maxX, minY, minZ, u0, v1, packedLight, packedOverlay, red, green, blue, alpha, 1.0F, 0.0F, 0.0F);
            }
        }
    }

    private void vertex(VertexConsumer consumer, PoseStack.Pose pose, float x, float y, float z, float u, float v, int packedLight, int packedOverlay, int red, int green, int blue, int alpha, float normalX, float normalY, float normalZ) {
        consumer.addVertex(pose, x, y, z)
                .setColor(red, green, blue, alpha)
                .setUv(u, v)
                .setOverlay(packedOverlay)
                .setLight(packedLight)
                .setNormal(pose, normalX, normalY, normalZ);
    }

    private float snowHeight(BlockState overlayState) {
        if (overlayState.is(Blocks.SNOW) && overlayState.hasProperty(SnowLayerBlock.LAYERS)) {
            return overlayState.getValue(SnowLayerBlock.LAYERS);
        }
        return 8.0F;
    }
}
