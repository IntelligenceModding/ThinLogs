package de.artemis.thinlogs.common.registration;

import de.artemis.thinlogs.common.blocks.ThinLogBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class ModBlockEntities {
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ThinLogBlockEntity>> THIN_LOG = Registration.BLOCK_ENTITY_TYPES.register(
            "thin_log",
            () -> BlockEntityType.Builder.of(ThinLogBlockEntity::new, ModBlocks.allThinLogBlocks()).build(null)
    );

    private ModBlockEntities() {
    }

    public static void register() {
    }
}
