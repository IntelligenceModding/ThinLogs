package de.artemis.thinlogs.common.blocks;

import de.artemis.thinlogs.common.registration.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public class ThinLogBlockEntity extends BlockEntity {
    private static final String OVERLAY_KEY = "overlay";

    @Nullable
    private BlockState overlayState;

    public ThinLogBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.THIN_LOG.get(), blockPos, blockState);
    }

    @Nullable
    public BlockState getOverlayState() {
        return overlayState;
    }

    public void setOverlayState(@Nullable BlockState overlayState) {
        this.overlayState = overlayState;
        setChanged();
        if (level != null) {
            BlockState state = getBlockState();
            level.sendBlockUpdated(worldPosition, state, state, 3);
        }
    }

    public void clearOverlay() {
        setOverlayState(null);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (overlayState != null) {
            tag.put(OVERLAY_KEY, NbtUtils.writeBlockState(overlayState));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        overlayState = tag.contains(OVERLAY_KEY) ? NbtUtils.readBlockState(registries.lookupOrThrow(net.minecraft.core.registries.Registries.BLOCK), tag.getCompound(OVERLAY_KEY)) : null;
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
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
    }
}
