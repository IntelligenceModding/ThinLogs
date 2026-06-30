package de.artemis.thinlogs.common.events;

import de.artemis.thinlogs.ThinLogs;
import de.artemis.thinlogs.common.blocks.ThinLogBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = ThinLogs.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public final class CommonEvents {
    private CommonEvents() {
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = player.level();
        ItemStack itemStack = player.getItemInHand(event.getHand());
        if (!ThinLogBlock.shouldInterceptRightClick(itemStack, player)) {
            return;
        }

        Direction face = event.getFace();
        if (face == null) {
            return;
        }

        ItemInteractionResult result = ThinLogBlock.tryToggleConnection(
                itemStack,
                level.getBlockState(event.getPos()),
                level,
                event.getPos(),
                player,
                event.getHitVec()
        );
        if (!result.consumesAction()) {
            result = ThinLogBlock.tryApplyOverlay(itemStack, level, event.getPos(), player);
        }

        if (!result.consumesAction() && ThinLogBlock.canRemoveOverlayWithItem(itemStack)) {
            result = ThinLogBlock.tryRemoveOverlay(itemStack, level, event.getPos(), player);
        }

        if (!result.consumesAction()) {
            BlockPos adjacentPos = event.getPos().relative(face);
            result = ThinLogBlock.tryApplyOverlay(itemStack, level, adjacentPos, player);
            if (!result.consumesAction() && ThinLogBlock.canRemoveOverlayWithItem(itemStack)) {
                result = ThinLogBlock.tryRemoveOverlay(itemStack, level, adjacentPos, player);
            }
        }

        if (result.consumesAction()) {
            event.setCanceled(true);
            event.setCancellationResult(result.result());
        }
    }
}
