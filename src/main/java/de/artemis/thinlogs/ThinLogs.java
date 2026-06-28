package de.artemis.thinlogs;

import de.artemis.thinlogs.common.registration.Registration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(ThinLogs.MOD_ID)
public class ThinLogs {
    public static final String MOD_ID = "thinlogs";

    public ThinLogs(IEventBus modEventBus) {
        Registration.register(modEventBus);
    }
}
