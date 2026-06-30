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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.client.model.data.ModelData;
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
        if (level != null && !level.isClientSide) {
            ThinLogBlock.refreshThinLogState(level, worldPosition);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (foliageOverlayState != null) {
            tag.put(FOLIAGE_OVERLAY_KEY, NbtUtils.writeBlockState(foliageOverlayState));
        }
        if (surfaceOverlayState != null) {
            tag.put(SURFACE_OVERLAY_KEY, NbtUtils.writeBlockState(surfaceOverlayState));
        }
        if (disabledConnectionMask != 0) {
            tag.putInt(DISABLED_CONNECTION_MASK_KEY, disabledConnectionMask);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        foliageOverlayState = tag.contains(FOLIAGE_OVERLAY_KEY)
                ? NbtUtils.readBlockState(registries.lookupOrThrow(net.minecraft.core.registries.Registries.BLOCK), tag.getCompound(FOLIAGE_OVERLAY_KEY))
                : null;
        surfaceOverlayState = tag.contains(SURFACE_OVERLAY_KEY)
                ? NbtUtils.readBlockState(registries.lookupOrThrow(net.minecraft.core.registries.Registries.BLOCK), tag.getCompound(SURFACE_OVERLAY_KEY))
                : null;
        if (tag.contains(OVERLAY_KEY)) {
            BlockState legacyOverlayState = NbtUtils.readBlockState(registries.lookupOrThrow(net.minecraft.core.registries.Registries.BLOCK), tag.getCompound(OVERLAY_KEY));
            switch (ThinLogOverlay.type(legacyOverlayState)) {
                case LEAVES -> foliageOverlayState = legacyOverlayState;
                case CARPET, SNOW -> surfaceOverlayState = legacyOverlayState;
                case NONE -> {
                }
            }
        }
        disabledConnectionMask = tag.getInt(DISABLED_CONNECTION_MASK_KEY);
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
        if (ThinLogOverlay.type(foliageOverlayState) != ThinLogOverlay.OverlayType.LEAVES) {
            return ModelData.EMPTY;
        }

        return ModelData.of(ThinLogModelData.FOLIAGE_OVERLAY, foliageOverlayState);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            loadAdditional(tag, lookupProvider);
            requestModelDataUpdate();
            rerenderClientBlock();
        }
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        loadAdditional(tag, registries);
        requestModelDataUpdate();
        rerenderClientBlock();
    }

    private void rerenderClientBlock() {
        if (level != null && level.isClientSide) {
            BlockState state = getBlockState();
            level.sendBlockUpdated(worldPosition, state, state, MODEL_DATA_UPDATE_FLAGS);
        }
    }
}
