package de.artemis.thinlogs.client.events;

import de.artemis.thinlogs.ThinLogs;
import de.artemis.thinlogs.client.render.ThinLogBlockEntityRenderer;
import de.artemis.thinlogs.common.registration.ModBlockEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = ThinLogs.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public final class ClientEvents {
    private ClientEvents() {
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.THIN_LOG.get(), ThinLogBlockEntityRenderer::new);
    }
}
