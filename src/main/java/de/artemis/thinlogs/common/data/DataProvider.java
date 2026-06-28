package de.artemis.thinlogs.common.data;

import de.artemis.thinlogs.ThinLogs;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ThinLogs.MOD_ID)
public class DataProvider {
    @SubscribeEvent
    public static void onDataGen(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> future = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new ModelAndBlockStateProvider(generator.getPackOutput(), existingFileHelper));
        generator.addProvider(event.includeClient(), new ItemModelProvider(generator.getPackOutput(), existingFileHelper));
        generator.addProvider(event.includeClient(), new LanguageProvider(generator.getPackOutput(), "en_us"));
        generator.addProvider(event.includeServer(), new BlockLootTablesProvider(generator.getPackOutput(), future));
        generator.addProvider(event.includeServer(), new TagsProvider.ItemTagsProvider(generator.getPackOutput(), future, existingFileHelper));
        generator.addProvider(event.includeServer(), new TagsProvider.BlockTagsProvider(generator.getPackOutput(), future, existingFileHelper));
        generator.addProvider(event.includeServer(), new RecipesProvider(generator.getPackOutput(), future));
    }

    public static String getRegistryName(Item item) {
        return item.builtInRegistryHolder().key().location().toString();
    }
}
