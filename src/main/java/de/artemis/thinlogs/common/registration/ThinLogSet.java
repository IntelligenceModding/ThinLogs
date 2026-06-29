package de.artemis.thinlogs.common.registration;

import de.artemis.thinlogs.common.blocks.ThinLogBlock;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;

public record ThinLogSet(
        ThinLogSetDefinition definition,
        DeferredBlock<ThinLogBlock> thinBlock,
        DeferredBlock<ThinLogBlock> strippedThinBlock
) {
    public List<? extends Block> allBlocks() {
        return List.of(thinBlock.get(), strippedThinBlock.get());
    }
}
