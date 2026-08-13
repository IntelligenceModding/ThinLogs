package de.artemis.thinlogs.client.model;

import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.model.data.ModelProperty;

public final class ThinLogModelData {
    public static final ModelProperty<BlockState> FOLIAGE_OVERLAY = new ModelProperty<>();
    public static final ModelProperty<BlockState> SURFACE_OVERLAY = new ModelProperty<>();

    private ThinLogModelData() {
    }
}
