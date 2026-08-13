package de.artemis.thinlogs;

import de.artemis.thinlogs.client.events.ClientEvents;
import de.artemis.thinlogs.common.registration.Registration;
import de.artemis.thinlogs.common.events.CommonEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(ThinLogs.MOD_ID)
public class ThinLogs {
    public static final String MOD_ID = "thinlogs";

    public ThinLogs(IEventBus modEventBus) {
        Registration.register(modEventBus);
        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            ClientEvents.registerModEventListeners(modEventBus);
        }
        NeoForge.EVENT_BUS.register(CommonEvents.class);
    }
}
