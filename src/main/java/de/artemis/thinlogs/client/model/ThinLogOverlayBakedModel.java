package de.artemis.thinlogs.client.model;

import de.artemis.thinlogs.common.blocks.ThinLogOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.DelegateBlockStateModel;
import net.neoforged.neoforge.common.extensions.IBlockGetterExtension;
import net.neoforged.neoforge.model.data.ModelData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ThinLogOverlayBakedModel extends DelegateBlockStateModel {
    public ThinLogOverlayBakedModel(BlockStateModel originalModel) {
        super(originalModel);
    }

    @Override
    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockStateModelPart> parts) {
        delegate.collectParts(level, pos, state, random, parts);

        ModelData modelData = ((IBlockGetterExtension) level).getModelData(pos);
        appendOverlayParts(level, pos, modelData.get(ThinLogModelData.FOLIAGE_OVERLAY), parts);
        appendOverlayParts(level, pos, modelData.get(ThinLogModelData.SURFACE_OVERLAY), parts);
    }

    private static void appendOverlayParts(BlockAndTintGetter level, BlockPos pos, @Nullable BlockState overlayState, List<BlockStateModelPart> parts) {
        if (overlayState == null || ThinLogOverlay.type(overlayState) == ThinLogOverlay.OverlayType.NONE) {
            return;
        }

        BlockStateModel overlayModel = Minecraft.getInstance().getModelManager().getBlockStateModelSet().get(overlayState);
        List<BlockStateModelPart> overlayParts = new ArrayList<>();
        overlayModel.collectParts(level, pos, overlayState, overlayRandom(overlayState), overlayParts);
        parts.addAll(overlayParts);
    }

    private static RandomSource overlayRandom(BlockState overlayState) {
        long seed = 0x9E3779B97F4A7C15L;
        seed = 31L * seed + overlayState.hashCode();
        return RandomSource.create(seed);
    }

}
