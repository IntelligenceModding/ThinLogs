package de.artemis.thinlogs;

import de.artemis.thinlogs.common.registration.ModBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class InventoryTab {

    public static void createInventoryTab(CreativeModeTab.Builder builder) {
        builder.displayItems((set, out) -> {

            Item[] inventoryTabItems = {
                    ModBlocks.THIN_OAK_LOG.get().asItem(), ModBlocks.THIN_STRIPPED_OAK_LOG.get().asItem(), ModBlocks.THIN_BIRCH_LOG.get().asItem(), ModBlocks.THIN_STRIPPED_BIRCH_LOG.get().asItem(), ModBlocks.THIN_SPRUCE_LOG.get().asItem(), ModBlocks.THIN_STRIPPED_SPRUCE_LOG.get().asItem(), ModBlocks.THIN_DARK_OAK_LOG.get().asItem(), ModBlocks.THIN_STRIPPED_DARK_OAK_LOG.get().asItem(), ModBlocks.THIN_ACACIA_LOG.get().asItem(), ModBlocks.THIN_STRIPPED_ACACIA_LOG.get().asItem(), ModBlocks.THIN_JUNGLE_LOG.get().asItem(), ModBlocks.THIN_STRIPPED_JUNGLE_LOG.get().asItem(), ModBlocks.THIN_MANGROVE_LOG.get().asItem(), ModBlocks.THIN_STRIPPED_MANGROVE_LOG.get().asItem(),ModBlocks.THIN_CRIMSON_STEM.get().asItem(), ModBlocks.THIN_STRIPPED_CRIMSON_STEM.get().asItem(), ModBlocks.THIN_WARPED_STEM.get().asItem(), ModBlocks.THIN_STRIPPED_WARPED_STEM.get().asItem(), ModBlocks.THIN_CHERRY_LOG.get().asItem(), ModBlocks.THIN_STRIPPED_CHERRY_LOG.get().asItem(), ModBlocks.THIN_BAMBOO_BLOCK.get().asItem(), ModBlocks.THIN_STRIPPED_BAMBOO_BLOCK.get().asItem()
            };
            for (Item inventoryTabItem : inventoryTabItems) {
                out.accept(inventoryTabItem);
            }

        });
        builder.icon(() -> ModBlocks.THIN_OAK_LOG.get().asItem().getDefaultInstance());
        builder.title(Component.translatable("itemGroup.thinlogs"));
    }
}
