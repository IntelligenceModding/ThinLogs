package de.artemis.thinlogs;

import com.mojang.logging.LogUtils;
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
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collections;

@Mod(ThinLogs.MOD_ID)
public class ThinLogs {

    public static final String MOD_ID = "thinlogs";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ThinLogs() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        Registration.register();
    }
}
