package de.artemis.thinlogs.common.data;

import de.artemis.thinlogs.ThinLogs;
import de.artemis.thinlogs.common.registration.ModBlocks;
import de.artemis.thinlogs.common.registration.ThinLogSet;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, ThinLogs.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (ThinLogSet set : ModBlocks.allSets()) {
            withExistingParent(DataProvider.getRegistryName(set.thinBlock().get().asItem()), ResourceLocation.fromNamespaceAndPath(ThinLogs.MOD_ID, "block/" + set.definition().thinId() + "_inventory"));
            withExistingParent(DataProvider.getRegistryName(set.strippedThinBlock().get().asItem()), ResourceLocation.fromNamespaceAndPath(ThinLogs.MOD_ID, "block/" + set.definition().strippedThinId() + "_inventory"));
        }
    }
}
