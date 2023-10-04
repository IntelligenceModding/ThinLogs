package de.artemis.thinlogs.common.data;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BlockLootTablesProvider extends LootTableProvider {

    public BlockLootTablesProvider(PackOutput packOutput) {
        super(packOutput, Set.of(), ImmutableList.of(new SubProviderEntry(BlockLootTables::new, LootContextParamSets.BLOCK)));
    }

    @NotNull
    @Override
    public List<SubProviderEntry> getTables() {
        return ImmutableList.of(new SubProviderEntry(BlockLootTables::new, LootContextParamSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, @NotNull ValidationContext validationtracker) {
        map.forEach((id, table) -> table.validate(validationtracker));
    }

}
