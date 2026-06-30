package de.artemis.thinlogs.client.model;

import de.artemis.thinlogs.common.blocks.ThinLogOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ThinLogOverlayBakedModel extends BakedModelWrapper<BakedModel> {
    public ThinLogOverlayBakedModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData extraData, @Nullable RenderType renderType) {
        List<BakedQuad> quads = new ArrayList<>(originalModel.getQuads(state, side, rand, extraData, renderType));
        BlockState foliageOverlayState = foliageOverlay(extraData);
        if (foliageOverlayState == null) {
            return quads;
        }

        BakedModel overlayModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(foliageOverlayState);
        if (renderType == null || overlayModel.getRenderTypes(foliageOverlayState, overlayRandom(foliageOverlayState, null, null), ModelData.EMPTY).contains(renderType)) {
            quads.addAll(overlayModel.getQuads(foliageOverlayState, side, overlayRandom(foliageOverlayState, side, renderType), ModelData.EMPTY, renderType));
        }
        return quads;
    }

    @Override
    public ChunkRenderTypeSet getRenderTypes(BlockState state, RandomSource rand, ModelData data) {
        BlockState foliageOverlayState = foliageOverlay(data);
        if (foliageOverlayState == null) {
            return originalModel.getRenderTypes(state, rand, data);
        }

        BakedModel overlayModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(foliageOverlayState);
        return ChunkRenderTypeSet.union(
                originalModel.getRenderTypes(state, rand, data),
                overlayModel.getRenderTypes(foliageOverlayState, overlayRandom(foliageOverlayState, null, null), ModelData.EMPTY)
        );
    }

    @Nullable
    private static BlockState foliageOverlay(ModelData data) {
        if (!data.has(ThinLogModelData.FOLIAGE_OVERLAY)) {
            return null;
        }

        BlockState foliageOverlayState = data.get(ThinLogModelData.FOLIAGE_OVERLAY);
        return ThinLogOverlay.type(foliageOverlayState) == ThinLogOverlay.OverlayType.LEAVES ? foliageOverlayState : null;
    }

    private static RandomSource overlayRandom(BlockState overlayState, @Nullable Direction side, @Nullable RenderType renderType) {
        long seed = 0x9E3779B97F4A7C15L;
        seed = 31L * seed + overlayState.hashCode();
        seed = 31L * seed + (side == null ? 0 : side.ordinal() + 1);
        seed = 31L * seed + (renderType == null ? 0 : renderType.toString().hashCode());
        return RandomSource.create(seed);
    }
}
