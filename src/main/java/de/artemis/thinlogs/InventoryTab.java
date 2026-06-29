package de.artemis.thinlogs;

import de.artemis.thinlogs.common.registration.ModBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public final class InventoryTab {
    private InventoryTab() {
    }

    public static void createInventoryTab(CreativeModeTab.Builder builder) {
        builder.displayItems((parameters, output) -> ModBlocks.allSets().forEach(set -> {
            output.accept(set.thinBlock().get());
            output.accept(set.strippedThinBlock().get());
        }));
        builder.icon(() -> ModBlocks.OAK.thinBlock().get().asItem().getDefaultInstance());
        builder.title(Component.translatable("itemGroup.thinlogs"));
    }
}
