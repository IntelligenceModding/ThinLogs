package de.artemis.thinlogs.common.data;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class BlockLootTablesProvider extends LootTableProvider {
    public BlockLootTablesProvider() {
        super(Set.of(), List.of(new SubProviderEntry(BlockLootTables::new, LootContextParamSets.BLOCK)));
    }
}
