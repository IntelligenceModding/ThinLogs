package de.artemis.thinlogs.client.events;

import de.artemis.thinlogs.ThinLogs;
import de.artemis.thinlogs.client.model.ThinLogOverlayBakedModel;
import de.artemis.thinlogs.client.render.ThinLogBlockEntityRenderer;
import de.artemis.thinlogs.common.blocks.ThinLogBlockEntity;
import de.artemis.thinlogs.common.blocks.ThinLogOverlay;
import de.artemis.thinlogs.common.registration.ModBlockEntities;
import de.artemis.thinlogs.common.registration.ModBlocks;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.util.HashSet;
import java.util.Set;

@EventBusSubscriber(modid = ThinLogs.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public final class ClientEvents {
    private static final Set<ResourceLocation> THIN_LOG_MODEL_IDS = collectThinLogModelIds();

    private ClientEvents() {
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.THIN_LOG.get(), ThinLogBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void wrapThinLogModels(ModelEvent.ModifyBakingResult event) {
        event.getModels().replaceAll((modelLocation, bakedModel) -> shouldWrap(modelLocation) ? new ThinLogOverlayBakedModel(bakedModel) : bakedModel);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(
                (state, level, pos, tintIndex) -> delegatedLeafColor(level, pos, tintIndex, event),
                ModBlocks.allThinLogBlocks()
        );
    }

    private static boolean shouldWrap(ModelResourceLocation modelLocation) {
        return THIN_LOG_MODEL_IDS.contains(modelLocation.id());
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

    private static Set<ResourceLocation> collectThinLogModelIds() {
        Set<ResourceLocation> ids = new HashSet<>();
        ModBlocks.allSets().forEach(set -> {
            ids.add(set.thinBlock().getId());
            ids.add(set.strippedThinBlock().getId());
        });
        return ids;
    }
}
