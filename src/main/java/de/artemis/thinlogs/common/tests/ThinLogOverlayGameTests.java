package de.artemis.thinlogs.common.tests;

import de.artemis.thinlogs.common.blocks.ThinLogBlockEntity;
import de.artemis.thinlogs.common.blocks.ThinLogOverlay;
import de.artemis.thinlogs.common.registration.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.player.Player;

public final class ThinLogOverlayGameTests {
    private static final BlockPos TEST_POS = new BlockPos(1, 2, 1);

    private ThinLogOverlayGameTests() {
    }

    public static void oakLeavesCanOverlayThinOakLog(GameTestHelper helper) {
        helper.setBlock(TEST_POS, ModBlocks.OAK.thinBlock().get());

        ThinLogBlockEntity beforeUse = helper.getBlockEntity(TEST_POS, ThinLogBlockEntity.class);
        helper.assertTrue(beforeUse != null, "Thin log block entity was not created");

        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Blocks.OAK_LEAVES));
        helper.useBlock(TEST_POS, player);

        ThinLogBlockEntity afterUse = helper.getBlockEntity(TEST_POS, ThinLogBlockEntity.class);
        helper.assertTrue(afterUse != null, "Thin log block entity missing after use");
        helper.assertTrue(afterUse.getOverlayState() != null, "Leaf overlay was not stored");
        helper.assertTrue(
                ThinLogOverlay.type(afterUse.getOverlayState()) == ThinLogOverlay.OverlayType.LEAVES,
                "Stored overlay was not leaves"
        );
        helper.succeed();
    }
}
