package de.artemis.thinlogs.client.events;

import de.artemis.thinlogs.ThinLogs;
import de.artemis.thinlogs.common.blockStateProperties.AppliedOnThinLogBlock;
import de.artemis.thinlogs.common.registration.ModBlocks;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.FoliageColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static de.artemis.thinlogs.common.blockStateProperties.ModBlockStateProperties.APPLIED_ON_THIN_LOG_BLOCK;

@Mod.EventBusSubscriber(modid = ThinLogs.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    @SubscribeEvent
    public static void onBlockColorEvent(RegisterColorHandlersEvent.Item event) {

        event.getBlockColors().register((state, level, pos, tint) -> {
                    AppliedOnThinLogBlock appliedOnThinLogBlock = state.getValue(APPLIED_ON_THIN_LOG_BLOCK);
                    if (appliedOnThinLogBlock == AppliedOnThinLogBlock.BIRCH_LEAVES) {
                        return FoliageColor.getBirchColor();
                    } else if (appliedOnThinLogBlock == AppliedOnThinLogBlock.SPRUCE_LEAVES) {
                        return FoliageColor.getEvergreenColor();
                    } else if (appliedOnThinLogBlock == AppliedOnThinLogBlock.OAK_LEAVES && level != null && pos != null) {
                        return BiomeColors.getAverageFoliageColor(level, pos);
                    } else if (appliedOnThinLogBlock == AppliedOnThinLogBlock.DARK_OAK_LEAVES && level != null && pos != null) {
                        return BiomeColors.getAverageFoliageColor(level, pos);
                    } else if (appliedOnThinLogBlock == AppliedOnThinLogBlock.ACACIA_LEAVES && level != null && pos != null) {
                        return BiomeColors.getAverageFoliageColor(level, pos);
                    } else if (appliedOnThinLogBlock == AppliedOnThinLogBlock.JUNGLE_LEAVES && level != null && pos != null) {
                        return BiomeColors.getAverageFoliageColor(level, pos);
                    } else if (appliedOnThinLogBlock == AppliedOnThinLogBlock.MANGROVE_LEAVES && level != null && pos != null) {
                        return BiomeColors.getAverageFoliageColor(level, pos);
                    }
                    return FoliageColor.getDefaultColor();
                }, ModBlocks.THIN_OAK_LOG.get(), ModBlocks.THIN_STRIPPED_OAK_LOG.get(), ModBlocks.THIN_BIRCH_LOG.get(), ModBlocks.THIN_STRIPPED_BIRCH_LOG.get(), ModBlocks.THIN_SPRUCE_LOG.get(), ModBlocks.THIN_STRIPPED_SPRUCE_LOG.get(), ModBlocks.THIN_DARK_OAK_LOG.get(), ModBlocks.THIN_STRIPPED_DARK_OAK_LOG.get(), ModBlocks.THIN_ACACIA_LOG.get(), ModBlocks.THIN_STRIPPED_ACACIA_LOG.get(), ModBlocks.THIN_JUNGLE_LOG.get(), ModBlocks.THIN_STRIPPED_JUNGLE_LOG.get(), ModBlocks.THIN_MANGROVE_LOG.get(), ModBlocks.THIN_STRIPPED_MANGROVE_LOG.get(),ModBlocks.THIN_CRIMSON_STEM.get(), ModBlocks.THIN_STRIPPED_CRIMSON_STEM.get(), ModBlocks.THIN_WARPED_STEM.get(), ModBlocks.THIN_STRIPPED_WARPED_STEM.get());

    }
}
