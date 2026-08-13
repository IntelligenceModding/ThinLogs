package de.artemis.thinlogs.client.events;

import de.artemis.thinlogs.client.model.ThinLogOverlayBakedModel;
import de.artemis.thinlogs.common.blocks.ThinLogBlock;
import de.artemis.thinlogs.client.render.ThinLogBlockEntityRenderer;
import de.artemis.thinlogs.common.blocks.ThinLogBlockEntity;
import de.artemis.thinlogs.common.blocks.ThinLogOverlay;
import de.artemis.thinlogs.common.registration.ModBlockEntities;
import de.artemis.thinlogs.common.registration.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public final class ClientEvents {
    private ClientEvents() {
    }

    public static void registerModEventListeners(IEventBus modEventBus) {
        modEventBus.addListener(ClientEvents::registerRenderers);
        modEventBus.addListener(ClientEvents::wrapThinLogModels);
        modEventBus.addListener(ClientEvents::registerBlockColors);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.THIN_LOG.get(), ThinLogBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void wrapThinLogModels(ModelEvent.ModifyBakingResult event) {
        event.getBakingResult().blockStateModels().replaceAll((state, model) -> shouldWrap(state) ? new ThinLogOverlayBakedModel(model) : model);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(
                (state, level, pos, tintIndex) -> delegatedLeafColor(level, pos, tintIndex, event),
                ModBlocks.allThinLogBlocks()
        );
    }

    private static boolean shouldWrap(BlockState state) {
        return state.getBlock() instanceof ThinLogBlock;
    }

    private static int delegatedLeafColor(BlockAndTintGetter level, BlockPos pos, int tintIndex, RegisterColorHandlersEvent.Block event) {
        if (level == null || pos == null || tintIndex < 0) {
            return -1;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof ThinLogBlockEntity thinLogBlockEntity)) {
            return -1;
        }

        BlockState foliageOverlayState = thinLogBlockEntity.getFoliageOverlayState();
        if (ThinLogOverlay.type(foliageOverlayState) != ThinLogOverlay.OverlayType.LEAVES) {
            return -1;
        }

        return event.getBlockColors().getColor(foliageOverlayState, level, pos, tintIndex);
    }

}
