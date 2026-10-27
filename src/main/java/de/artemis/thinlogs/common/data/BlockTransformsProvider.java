package de.artemis.thinlogs.common.data;

import de.artemis.thinlogs.common.registration.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Transformable;

import java.util.concurrent.CompletableFuture;

public class BlockTransformsProvider extends DataMapProvider {
    public BlockTransformsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        var transforms = builder(NeoForgeDataMaps.TRANSFORMABLES);
        ModBlocks.allSets().forEach(set -> transforms.add(set.thinBlock(),
                Transformable.stripping(set.thinBlock().get(), set.strippedThinBlock().get()), false));
    }
}
