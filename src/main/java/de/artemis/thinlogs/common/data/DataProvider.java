package de.artemis.thinlogs.common.data;

import de.artemis.thinlogs.ThinLogs;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ThinLogs.MOD_ID)
public class DataProvider {
    @SubscribeEvent
    public static void onClientDataGen(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        event.addProvider(new ModelAndBlockStateProvider(generator.getPackOutput()));
        event.addProvider(new LanguageProvider(generator.getPackOutput(), "en_us"));
        addServerProviders(event);
    }

    @SubscribeEvent
    public static void onServerDataGen(GatherDataEvent.Server event) {
        addServerProviders(event);
    }

    private static void addServerProviders(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        CompletableFuture<HolderLookup.Provider> future = event.getLookupProvider();

        event.addProvider(new BlockLootTablesProvider(generator.getPackOutput(), future));
        event.addProvider(new TagsProvider.BlockTagsProvider(generator.getPackOutput(), future));
        event.addProvider(new TagsProvider.ItemTagsProvider(generator.getPackOutput(), future));
        event.addProvider(new ModDatapackProvider(generator.getPackOutput(), future));
        event.addProvider(new RecipesProvider.Runner(generator.getPackOutput(), future));
    }

    public static String getRegistryName(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).toString();
    }
}
