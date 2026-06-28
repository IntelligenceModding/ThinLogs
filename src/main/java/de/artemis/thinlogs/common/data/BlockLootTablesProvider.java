package de.artemis.thinlogs.common.data;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class BlockLootTablesProvider extends LootTableProvider {
    public BlockLootTablesProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, Set.<ResourceKey<LootTable>>of(), ImmutableList.of(new SubProviderEntry(provider -> new BlockLootTables(provider), LootContextParamSets.BLOCK)), registries);
    }

    @NotNull
    @Override
    public List<SubProviderEntry> getTables() {
        return ImmutableList.of(new SubProviderEntry(provider -> new BlockLootTables(provider), LootContextParamSets.BLOCK));
    }
}
