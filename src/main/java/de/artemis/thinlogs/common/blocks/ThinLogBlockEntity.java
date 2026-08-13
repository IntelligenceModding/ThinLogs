package de.artemis.thinlogs.common.blocks;

import de.artemis.thinlogs.client.model.ThinLogModelData;
import de.artemis.thinlogs.common.registration.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.model.data.ModelData;
import org.jetbrains.annotations.Nullable;

public class ThinLogBlockEntity extends BlockEntity {
    private static final int MODEL_DATA_UPDATE_FLAGS = 3 | 8;
    private static final String OVERLAY_KEY = "overlay";
    private static final String FOLIAGE_OVERLAY_KEY = "foliage_overlay";
    private static final String SURFACE_OVERLAY_KEY = "surface_overlay";
    private static final String DISABLED_CONNECTION_MASK_KEY = "disabled_connection_mask";

    @Nullable
    private BlockState foliageOverlayState;
    @Nullable
    private BlockState surfaceOverlayState;
    private int disabledConnectionMask;

    public ThinLogBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.THIN_LOG.get(), blockPos, blockState);
    }

    @Nullable
    public BlockState getOverlayState() {
        return surfaceOverlayState != null ? surfaceOverlayState : foliageOverlayState;
    }

    @Nullable
    public BlockState getFoliageOverlayState() {
        return foliageOverlayState;
    }

    public void setFoliageOverlayState(@Nullable BlockState overlayState) {
        this.foliageOverlayState = overlayState;
        setChanged();
        if (level != null) {
            requestModelDataUpdate();
            BlockState state = getBlockState();
            level.sendBlockUpdated(worldPosition, state, state, MODEL_DATA_UPDATE_FLAGS);
        }
    }

    public void clearFoliageOverlay() {
        setFoliageOverlayState(null);
    }

    @Nullable
    public BlockState getSurfaceOverlayState() {
        return surfaceOverlayState;
    }

    public void setSurfaceOverlayState(@Nullable BlockState overlayState) {
        this.surfaceOverlayState = overlayState;
        setChanged();
        if (level != null) {
            requestModelDataUpdate();
            BlockState state = getBlockState();
            level.sendBlockUpdated(worldPosition, state, state, MODEL_DATA_UPDATE_FLAGS);
        }
    }

    public void clearSurfaceOverlay() {
        setSurfaceOverlayState(null);
    }

    public boolean isConnectionDisabled(Direction direction) {
        return (disabledConnectionMask & (1 << direction.get3DDataValue())) != 0;
    }

    public boolean setConnectionDisabled(Direction direction, boolean disabled) {
        int bit = 1 << direction.get3DDataValue();
        int nextMask = disabled ? disabledConnectionMask | bit : disabledConnectionMask & ~bit;
        if (nextMask == disabledConnectionMask) {
            return false;
        }

        disabledConnectionMask = nextMask;
        setChanged();
        return true;
    }

    public void copyPersistentStateTo(ThinLogBlockEntity target) {
        target.foliageOverlayState = this.foliageOverlayState;
        target.surfaceOverlayState = this.surfaceOverlayState;
        target.disabledConnectionMask = this.disabledConnectionMask;
        target.setChanged();
        target.requestModelDataUpdate();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (level != null && !level.isClientSide()) {
            ThinLogBlock.refreshThinLogState(level, worldPosition);
        }
    }

    @Override
    protected void saveAdditional(ValueOutput tag) {
        super.saveAdditional(tag);
        if (foliageOverlayState != null) {
            tag.store(FOLIAGE_OVERLAY_KEY, CompoundTag.CODEC, NbtUtils.writeBlockState(foliageOverlayState));
        }
        if (surfaceOverlayState != null) {
            tag.store(SURFACE_OVERLAY_KEY, CompoundTag.CODEC, NbtUtils.writeBlockState(surfaceOverlayState));
        }
        if (disabledConnectionMask != 0) {
            tag.putInt(DISABLED_CONNECTION_MASK_KEY, disabledConnectionMask);
        }
    }

    @Override
    protected void loadAdditional(ValueInput tag) {
        super.loadAdditional(tag);
        var blocks = tag.lookup().lookupOrThrow(net.minecraft.core.registries.Registries.BLOCK);
        foliageOverlayState = tag.read(FOLIAGE_OVERLAY_KEY, CompoundTag.CODEC)
                .map(overlayTag -> NbtUtils.readBlockState(blocks, overlayTag))
                .orElse(null);
        surfaceOverlayState = tag.read(SURFACE_OVERLAY_KEY, CompoundTag.CODEC)
                .map(overlayTag -> NbtUtils.readBlockState(blocks, overlayTag))
                .orElse(null);
        tag.read(OVERLAY_KEY, CompoundTag.CODEC).ifPresent(legacyTag -> {
            BlockState legacyOverlayState = NbtUtils.readBlockState(blocks, legacyTag);
            switch (ThinLogOverlay.type(legacyOverlayState)) {
                case LEAVES -> foliageOverlayState = legacyOverlayState;
                case CARPET, SNOW -> surfaceOverlayState = legacyOverlayState;
                case NONE -> {
                }
            }
        });
        disabledConnectionMask = tag.getIntOr(DISABLED_CONNECTION_MASK_KEY, 0);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public @Nullable ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public ModelData getModelData() {
        ModelData.Builder modelData = ModelData.builder();
        boolean hasOverlay = false;

        if (ThinLogOverlay.type(foliageOverlayState) == ThinLogOverlay.OverlayType.LEAVES) {
            modelData.with(ThinLogModelData.FOLIAGE_OVERLAY, foliageOverlayState);
            hasOverlay = true;
        }
        if (ThinLogOverlay.type(surfaceOverlayState) != ThinLogOverlay.OverlayType.NONE) {
            modelData.with(ThinLogModelData.SURFACE_OVERLAY, surfaceOverlayState);
            hasOverlay = true;
        }

        if (!hasOverlay) {
            return ModelData.EMPTY;
        }

        return modelData.build();
    }

    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            loadAdditional(TagValueInput.create(ProblemReporter.DISCARDING, lookupProvider, tag));
            requestModelDataUpdate();
            rerenderClientBlock();
        }
    }

    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        loadAdditional(TagValueInput.create(ProblemReporter.DISCARDING, registries, tag));
        requestModelDataUpdate();
        rerenderClientBlock();
    }

    private void rerenderClientBlock() {
        if (level != null && level.isClientSide()) {
            BlockState state = getBlockState();
            level.sendBlockUpdated(worldPosition, state, state, MODEL_DATA_UPDATE_FLAGS);
        }
    }
}
