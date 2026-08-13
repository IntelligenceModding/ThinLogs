package de.artemis.thinlogs.common.data;

import de.artemis.thinlogs.ThinLogs;
import de.artemis.thinlogs.common.registration.ModBlocks;
import de.artemis.thinlogs.common.registration.ModTags;
import de.artemis.thinlogs.common.registration.ThinLogSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public final class TagsProvider {
    private TagsProvider() {
    }

    public static class BlockTagsProvider extends net.neoforged.neoforge.common.data.BlockTagsProvider {
        protected BlockTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future) {
            super(packOutput, future, ThinLogs.MOD_ID);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            for (Block block : ModBlocks.allThinLogBlocks()) {
                tag(BlockTags.MINEABLE_WITH_AXE).add(block);
            }

            addSpeciesTags();
            addMinecraftLogTags();
            addCompatibilityTags();

            tag(ModTags.BlockTagsSet.THIN_LOG_OVERLAY_LEAVES).addTag(BlockTags.LEAVES);
            tag(ModTags.BlockTagsSet.THIN_LOG_OVERLAY_CARPETS)
                    .addTag(BlockTags.WOOL_CARPETS)
                    .add(Blocks.MOSS_CARPET);
            tag(ModTags.BlockTagsSet.THIN_LOG_OVERLAY_SNOW)
                    .add(Blocks.SNOW, Blocks.SNOW_BLOCK);
        }

        private void addSpeciesTags() {
            tag(BlockTags.OAK_LOGS).add(ModBlocks.OAK.thinBlock().get(), ModBlocks.OAK.strippedThinBlock().get());
            tag(BlockTags.BIRCH_LOGS).add(ModBlocks.BIRCH.thinBlock().get(), ModBlocks.BIRCH.strippedThinBlock().get());
            tag(BlockTags.SPRUCE_LOGS).add(ModBlocks.SPRUCE.thinBlock().get(), ModBlocks.SPRUCE.strippedThinBlock().get());
            tag(BlockTags.JUNGLE_LOGS).add(ModBlocks.JUNGLE.thinBlock().get(), ModBlocks.JUNGLE.strippedThinBlock().get());
            tag(BlockTags.ACACIA_LOGS).add(ModBlocks.ACACIA.thinBlock().get(), ModBlocks.ACACIA.strippedThinBlock().get());
            tag(BlockTags.DARK_OAK_LOGS).add(ModBlocks.DARK_OAK.thinBlock().get(), ModBlocks.DARK_OAK.strippedThinBlock().get());
            tag(BlockTags.PALE_OAK_LOGS).add(ModBlocks.PALE_OAK.thinBlock().get(), ModBlocks.PALE_OAK.strippedThinBlock().get());
            tag(BlockTags.MANGROVE_LOGS).add(ModBlocks.MANGROVE.thinBlock().get(), ModBlocks.MANGROVE.strippedThinBlock().get());
            tag(BlockTags.CHERRY_LOGS).add(ModBlocks.CHERRY.thinBlock().get(), ModBlocks.CHERRY.strippedThinBlock().get());
            tag(BlockTags.CRIMSON_STEMS).add(ModBlocks.CRIMSON.thinBlock().get(), ModBlocks.CRIMSON.strippedThinBlock().get());
            tag(BlockTags.WARPED_STEMS).add(ModBlocks.WARPED.thinBlock().get(), ModBlocks.WARPED.strippedThinBlock().get());
            tag(BlockTags.BAMBOO_BLOCKS).add(ModBlocks.BAMBOO.thinBlock().get(), ModBlocks.BAMBOO.strippedThinBlock().get());

            tag(BlockTags.OVERWORLD_NATURAL_LOGS).add(
                    ModBlocks.OAK.thinBlock().get(),
                    ModBlocks.BIRCH.thinBlock().get(),
                    ModBlocks.SPRUCE.thinBlock().get(),
                    ModBlocks.JUNGLE.thinBlock().get(),
                    ModBlocks.ACACIA.thinBlock().get(),
                    ModBlocks.DARK_OAK.thinBlock().get(),
                    ModBlocks.PALE_OAK.thinBlock().get(),
                    ModBlocks.MANGROVE.thinBlock().get(),
                    ModBlocks.CHERRY.thinBlock().get()
            );
        }

        private void addMinecraftLogTags() {
            tag(BlockTags.LOGS).add(blocks(ALL_LOG_SETS));
            tag(BlockTags.LOGS_THAT_BURN).add(blocks(FLAMMABLE_LOG_SETS));
            tag(BlockTags.COMPLETES_FIND_TREE_TUTORIAL).add(blocks(ALL_LOG_SETS));
        }

        private void addCompatibilityTags() {
            tag(Tags.Blocks.OVERWORLD_NATURAL_LOGS).add(thinBlocks(OVERWORLD_NATURAL_LOG_SETS));
            tag(Tags.Blocks.NETHER_NATURAL_LOGS).add(thinBlocks(NETHER_NATURAL_LOG_SETS));
            tag(Tags.Blocks.NATURAL_LOGS).add(thinBlocks(NATURAL_LOG_SETS));

            tag(Tags.Blocks.STRIPPED_LOGS).add(
                    ModBlocks.OAK.strippedThinBlock().get(),
                    ModBlocks.BIRCH.strippedThinBlock().get(),
                    ModBlocks.SPRUCE.strippedThinBlock().get(),
                    ModBlocks.JUNGLE.strippedThinBlock().get(),
                    ModBlocks.ACACIA.strippedThinBlock().get(),
                    ModBlocks.DARK_OAK.strippedThinBlock().get(),
                    ModBlocks.PALE_OAK.strippedThinBlock().get(),
                    ModBlocks.MANGROVE.strippedThinBlock().get(),
                    ModBlocks.CHERRY.strippedThinBlock().get(),
                    ModBlocks.BAMBOO.strippedThinBlock().get(),
                    ModBlocks.CRIMSON.strippedThinBlock().get(),
                    ModBlocks.WARPED.strippedThinBlock().get()
            );
        }

    }

    public static class ItemTagsProvider extends net.neoforged.neoforge.common.data.ItemTagsProvider {
        protected ItemTagsProvider(
                PackOutput packOutput,
                CompletableFuture<HolderLookup.Provider> future
        ) {
            super(packOutput, future, ThinLogs.MOD_ID);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            // Intentionally empty: thin log items should not satisfy full-log recipes,
            // fuels, or common item tag conventions at the same value as normal logs.
        }
    }

    private static final ThinLogSet[] OVERWORLD_NATURAL_LOG_SETS = {
            ModBlocks.OAK,
            ModBlocks.BIRCH,
            ModBlocks.SPRUCE,
            ModBlocks.JUNGLE,
            ModBlocks.ACACIA,
            ModBlocks.DARK_OAK,
            ModBlocks.PALE_OAK,
            ModBlocks.MANGROVE,
            ModBlocks.CHERRY
    };
    private static final ThinLogSet[] NETHER_NATURAL_LOG_SETS = {
            ModBlocks.CRIMSON,
            ModBlocks.WARPED
    };
    private static final ThinLogSet[] NATURAL_LOG_SETS = concat(OVERWORLD_NATURAL_LOG_SETS, NETHER_NATURAL_LOG_SETS);
    private static final ThinLogSet[] FLAMMABLE_LOG_SETS = OVERWORLD_NATURAL_LOG_SETS;
    private static final ThinLogSet[] ALL_LOG_SETS = NATURAL_LOG_SETS;

    private static Block[] blocks(ThinLogSet... sets) {
        Block[] blocks = new Block[sets.length * 2];
        int index = 0;
        for (ThinLogSet set : sets) {
            blocks[index++] = set.thinBlock().get();
            blocks[index++] = set.strippedThinBlock().get();
        }
        return blocks;
    }

    private static Block[] thinBlocks(ThinLogSet... sets) {
        Block[] blocks = new Block[sets.length];
        for (int i = 0; i < sets.length; i++) {
            blocks[i] = sets[i].thinBlock().get();
        }
        return blocks;
    }

    private static ThinLogSet[] concat(ThinLogSet[] first, ThinLogSet[] second) {
        ThinLogSet[] result = new ThinLogSet[first.length + second.length];
        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
