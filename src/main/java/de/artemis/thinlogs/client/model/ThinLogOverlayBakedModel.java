package de.artemis.thinlogs.client.model;

import de.artemis.thinlogs.common.blocks.ThinLogOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TriState;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.DelegateBlockStateModel;
import net.neoforged.neoforge.common.extensions.IBlockGetterExtension;
import net.neoforged.neoforge.model.data.ModelData;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ThinLogOverlayBakedModel extends DelegateBlockStateModel {
    public ThinLogOverlayBakedModel(BlockStateModel originalModel) {
        super(originalModel);
    }

    @Override
    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockModelPart> parts) {
        delegate.collectParts(level, pos, state, random, parts);

        ModelData modelData = ((IBlockGetterExtension) level).getModelData(pos);
        appendOverlayParts(level, pos, modelData.get(ThinLogModelData.FOLIAGE_OVERLAY), parts);
        appendOverlayParts(level, pos, modelData.get(ThinLogModelData.SURFACE_OVERLAY), parts);
    }

    private static void appendOverlayParts(BlockAndTintGetter level, BlockPos pos, @Nullable BlockState overlayState, List<BlockModelPart> parts) {
        if (overlayState == null || ThinLogOverlay.type(overlayState) == ThinLogOverlay.OverlayType.NONE) {
            return;
        }

        BlockStateModel overlayModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(overlayState);
        for (BlockModelPart part : overlayModel.collectParts(level, pos, overlayState, overlayRandom(overlayState))) {
            parts.add(new OverlayBlockModelPart(part, overlayState));
        }
    }

    private static RandomSource overlayRandom(BlockState overlayState) {
        long seed = 0x9E3779B97F4A7C15L;
        seed = 31L * seed + overlayState.hashCode();
        return RandomSource.create(seed);
    }

    private record OverlayBlockModelPart(BlockModelPart delegate, BlockState overlayState) implements BlockModelPart {
        @Override
        public List<BakedQuad> getQuads(@Nullable Direction direction) {
            return delegate.getQuads(direction);
        }

        @Override
        public boolean useAmbientOcclusion() {
            return delegate.useAmbientOcclusion();
        }

        @Override
        public TriState ambientOcclusion() {
            return delegate.ambientOcclusion();
        }

        @Override
        public net.minecraft.client.renderer.texture.TextureAtlasSprite particleIcon() {
            return delegate.particleIcon();
        }

        @Override
        public ChunkSectionLayer getRenderType(BlockState state) {
            return ItemBlockRenderTypes.getChunkRenderType(overlayState);
        }
    }
}
