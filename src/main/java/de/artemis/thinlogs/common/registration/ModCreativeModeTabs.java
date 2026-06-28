package de.artemis.thinlogs.common.registration;

import de.artemis.thinlogs.InventoryTab;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;

import static de.artemis.thinlogs.common.registration.Registration.CREATIVE_MODE_TABS;

public class ModCreativeModeTabs {
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> INVENTORY_TAB = CREATIVE_MODE_TABS.register("inventory_tab", ModCreativeModeTabs::createInventoryTab);

    private static CreativeModeTab createInventoryTab() {
        CreativeModeTab.Builder builder = new CreativeModeTab.Builder(CreativeModeTab.Row.BOTTOM, -1);
        InventoryTab.createInventoryTab(builder);
        return builder.build();
    }

    public static void register() {
    }
}
