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
import de.artemis.thinlogs.ThinLogs;
import de.artemis.thinlogs.common.blockStateProperties.ModBlockStateProperties;
import de.artemis.thinlogs.common.registration.ModTreeConfiguredFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.util.RandomSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.List;
import java.util.function.Consumer;

public final class ThinLogOverlayGameTests {
    private static final BlockPos TEST_POS = new BlockPos(1, 2, 1);
    private static final DeferredRegister<Consumer<GameTestHelper>> FUNCTIONS = DeferredRegister.create(Registries.TEST_FUNCTION, ThinLogs.MOD_ID);
    private static final List<String> TEST_NAMES = List.of("leaf_overlay", "axe_stripping", "thin_tree_placement",
            "poplar_leaf_overlays", "thin_poplar_placement");

    public static void register(IEventBus bus) {
        FUNCTIONS.register("leaf_overlay", () -> ThinLogOverlayGameTests::oakLeavesCanOverlayThinOakLog);
        FUNCTIONS.register("axe_stripping", () -> ThinLogOverlayGameTests::axeRemovesOverlayThenStripsPreservingConnections);
        FUNCTIONS.register("thin_tree_placement", () -> ThinLogOverlayGameTests::thinOakCanGenerate);
        FUNCTIONS.register("poplar_leaf_overlays", () -> ThinLogOverlayGameTests::poplarLeavesAndStrippingWork);
        FUNCTIONS.register("thin_poplar_placement", () -> ThinLogOverlayGameTests::thinPoplarsCanGenerate);
        FUNCTIONS.register(bus);
        bus.addListener(ThinLogOverlayGameTests::registerTests);
    }

    private static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(Identifier.fromNamespaceAndPath(ThinLogs.MOD_ID, "port_tests"),
                new TestEnvironmentDefinition.AllOf(List.of()));
        for (String name : TEST_NAMES) {
            Identifier id = Identifier.fromNamespaceAndPath(ThinLogs.MOD_ID, name);
            event.registerTest(id, new FunctionGameTestInstance(net.minecraft.resources.ResourceKey.create(Registries.TEST_FUNCTION, id),
                    new TestData<>(environment, net.minecraft.world.level.Level.OVERWORLD,
                            Identifier.withDefaultNamespace("empty"), 100, 0, true,
                            net.minecraft.world.level.block.Rotation.NONE, false, 1, 1, true, 16)));
        }
    }

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

    public static void axeRemovesOverlayThenStripsPreservingConnections(GameTestHelper helper) {
        helper.setBlock(TEST_POS.above(), Blocks.OAK_LOG);
        BlockState original = ModBlocks.OAK.thinBlock().get().defaultBlockState()
                .setValue(ModBlockStateProperties.UP, true);
        helper.setBlock(TEST_POS, original);
        helper.getBlockEntity(TEST_POS, ThinLogBlockEntity.class).setFoliageOverlayState(Blocks.OAK_LEAVES.defaultBlockState());
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.IRON_AXE));
        helper.useBlock(TEST_POS, player);
        helper.assertTrue(helper.getBlockState(TEST_POS).is(ModBlocks.OAK.thinBlock().get()),
                "First axe interaction should only remove the overlay");
        helper.assertTrue(helper.getBlockEntity(TEST_POS, ThinLogBlockEntity.class).getFoliageOverlayState() == null,
                "First axe interaction did not remove the leaf overlay");
        helper.useBlock(TEST_POS, player);
        BlockState stripped = helper.getBlockState(TEST_POS);
        helper.assertTrue(stripped.is(ModBlocks.OAK.strippedThinBlock().get()), "Axe did not strip the thin log");
        helper.assertTrue(stripped.getValue(ModBlockStateProperties.UP), "Stripping lost the upward connection");
        helper.succeed();
    }

    public static void thinOakCanGenerate(GameTestHelper helper) {
        var level = helper.getLevel();
        BlockPos origin = helper.absolutePos(TEST_POS).offset(0, 2, 0);
        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-8, -1, -8), origin.offset(8, 24, 8))) {
            level.setBlock(pos, pos.getY() < origin.getY() ? Blocks.DIRT.defaultBlockState() : Blocks.AIR.defaultBlockState(), 2);
        }
        var tree = level.registryAccess().getOrThrow(ModTreeConfiguredFeatures.THIN_OAK).value();
        helper.assertTrue(tree.place(level, level.getChunkSource().getGenerator(), RandomSource.create(42L), origin),
                "Thin oak feature failed to place");
        helper.assertTrue(level.getBlockState(origin).is(ModBlocks.OAK.thinBlock().get()), "Thin oak trunk is missing");
        helper.assertTrue(level.getBlockEntity(origin) instanceof ThinLogBlockEntity, "Generated trunk has no block entity");
        helper.succeed();
    }

    public static void poplarLeavesAndStrippingWork(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        for (var leaves : List.of(Blocks.RED_POPLAR_LEAVES, Blocks.ORANGE_POPLAR_LEAVES, Blocks.YELLOW_POPLAR_LEAVES)) {
            for (var log : ModBlocks.POPLAR.allBlocks()) {
                helper.setBlock(TEST_POS, Blocks.AIR);
                helper.setBlock(TEST_POS, log);
                player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(leaves));
                helper.useBlock(TEST_POS, player);
                var overlay = helper.getBlockEntity(TEST_POS, ThinLogBlockEntity.class).getFoliageOverlayState();
                helper.assertTrue(overlay != null && overlay.is(leaves), "Poplar leaf color was not stored on " + log);
                helper.assertTrue(player.getMainHandItem().isEmpty(), "Applying poplar leaves did not consume the item");
            }
        }
        helper.setBlock(TEST_POS, Blocks.AIR);
        helper.setBlock(TEST_POS, ModBlocks.POPLAR.thinBlock().get());
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.IRON_AXE));
        helper.useBlock(TEST_POS, player);
        helper.assertTrue(helper.getBlockState(TEST_POS).is(ModBlocks.POPLAR.strippedThinBlock().get()),
                "Axe did not strip the thin poplar log");
        helper.succeed();
    }

    public static void thinPoplarsCanGenerate(GameTestHelper helper) {
        var level = helper.getLevel();
        BlockPos origin = helper.absolutePos(TEST_POS).offset(0, 2, 0);
        var features = List.of(ModTreeConfiguredFeatures.THIN_RED_POPLAR,
                ModTreeConfiguredFeatures.THIN_ORANGE_POPLAR, ModTreeConfiguredFeatures.THIN_YELLOW_POPLAR);
        var leaves = List.of(Blocks.RED_POPLAR_LEAVES, Blocks.ORANGE_POPLAR_LEAVES, Blocks.YELLOW_POPLAR_LEAVES);
        for (int i = 0; i < features.size(); i++) {
            for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-8, -1, -8), origin.offset(8, 24, 8))) {
                level.setBlock(pos, pos.getY() < origin.getY() ? Blocks.DIRT.defaultBlockState() : Blocks.AIR.defaultBlockState(), 2);
            }
            var tree = level.registryAccess().getOrThrow(features.get(i)).value();
            helper.assertTrue(tree.place(level, level.getChunkSource().getGenerator(), RandomSource.create(42L), origin),
                    "Thin poplar feature failed to place: " + features.get(i));
            helper.assertTrue(level.getBlockState(origin).is(ModBlocks.POPLAR.thinBlock().get()), "Poplar trunk is missing");
            boolean foundLeaves = false;
            boolean foundOverlay = false;
            for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-8, 0, -8), origin.offset(8, 24, 8))) {
                foundLeaves |= level.getBlockState(pos).is(leaves.get(i));
                if (level.getBlockEntity(pos) instanceof ThinLogBlockEntity entity && entity.getFoliageOverlayState() != null) {
                    foundOverlay |= entity.getFoliageOverlayState().is(leaves.get(i));
                }
            }
            helper.assertTrue(foundLeaves, "Generated poplar has no leaves of the expected color");
            helper.assertTrue(foundOverlay, "Generated poplar has no leaf overlays of the expected color");
        }
        helper.succeed();
    }
}
