package de.artemis.thinlogs;

import de.artemis.thinlogs.common.registration.ModBlocks;
import de.artemis.thinlogs.common.registration.Registration;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

@Mod(ThinLogs.MOD_ID)
public class ThinLogs {

    public static final String MOD_ID = "thinlogs";
    public static final CreativeModeTab INVENTORY_TAB = new CreativeModeTab(MOD_ID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModBlocks.THIN_OAK_LOG.get().asItem());
        }

        @Override
        public void fillItemList(@NotNull NonNullList<ItemStack> items) {
            ArrayList<Item> blockList = new ArrayList<>();
            Collections.addAll(blockList, ModBlocks.THIN_OAK_LOG.get().asItem(), ModBlocks.THIN_STRIPPED_OAK_LOG.get().asItem(), ModBlocks.THIN_BIRCH_LOG.get().asItem(), ModBlocks.THIN_STRIPPED_BIRCH_LOG.get().asItem(), ModBlocks.THIN_SPRUCE_LOG.get().asItem(), ModBlocks.THIN_STRIPPED_SPRUCE_LOG.get().asItem(), ModBlocks.THIN_DARK_OAK_LOG.get().asItem(), ModBlocks.THIN_STRIPPED_DARK_OAK_LOG.get().asItem(), ModBlocks.THIN_ACACIA_LOG.get().asItem(), ModBlocks.THIN_STRIPPED_ACACIA_LOG.get().asItem(), ModBlocks.THIN_JUNGLE_LOG.get().asItem(), ModBlocks.THIN_STRIPPED_JUNGLE_LOG.get().asItem(), ModBlocks.THIN_MANGROVE_LOG.get().asItem(), ModBlocks.THIN_STRIPPED_MANGROVE_LOG.get().asItem(),ModBlocks.THIN_CRIMSON_STEM.get().asItem(), ModBlocks.THIN_STRIPPED_CRIMSON_STEM.get().asItem(), ModBlocks.THIN_WARPED_STEM.get().asItem(), ModBlocks.THIN_STRIPPED_WARPED_STEM.get().asItem());

            int run = 0;
            for (Item x : blockList) {
                items.add(run, new ItemStack(x));
                run++;
            }
        }
    };

    public ThinLogs() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        Registration.register();
    }

}
