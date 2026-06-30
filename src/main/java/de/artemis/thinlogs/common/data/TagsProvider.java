package de.artemis.thinlogs.common.data;

import de.artemis.thinlogs.ThinLogs;
import de.artemis.thinlogs.common.registration.ModBlocks;
import de.artemis.thinlogs.common.registration.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public final class TagsProvider {
    private TagsProvider() {
    }

    public static class BlockTagsProvider extends net.neoforged.neoforge.common.data.BlockTagsProvider {
        protected BlockTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper existingFileHelper) {
            super(packOutput, future, ThinLogs.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            for (Block block : ModBlocks.allThinLogBlocks()) {
                tag(BlockTags.MINEABLE_WITH_AXE).add(block);
            }

            addSpeciesTags();
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
                    ModBlocks.MANGROVE.thinBlock().get(),
                    ModBlocks.CHERRY.thinBlock().get()
            );
        }

        private void addCompatibilityTags() {
            tag(Tags.Blocks.STRIPPED_LOGS).add(
                    ModBlocks.OAK.strippedThinBlock().get(),
                    ModBlocks.BIRCH.strippedThinBlock().get(),
                    ModBlocks.SPRUCE.strippedThinBlock().get(),
                    ModBlocks.JUNGLE.strippedThinBlock().get(),
                    ModBlocks.ACACIA.strippedThinBlock().get(),
                    ModBlocks.DARK_OAK.strippedThinBlock().get(),
                    ModBlocks.MANGROVE.strippedThinBlock().get(),
                    ModBlocks.CHERRY.strippedThinBlock().get(),
                    ModBlocks.BAMBOO.strippedThinBlock().get(),
                    ModBlocks.CRIMSON.strippedThinBlock().get(),
                    ModBlocks.WARPED.strippedThinBlock().get()
            );
        }

    }

    public static class ItemTagsProvider extends net.minecraft.data.tags.ItemTagsProvider {
        protected ItemTagsProvider(
                PackOutput packOutput,
                CompletableFuture<HolderLookup.Provider> future,
                CompletableFuture<TagLookup<Block>> blockTagLookup,
                ExistingFileHelper existingFileHelper
        ) {
            super(packOutput, future, blockTagLookup, ThinLogs.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            copy(BlockTags.OAK_LOGS, ItemTags.OAK_LOGS);
            copy(BlockTags.BIRCH_LOGS, ItemTags.BIRCH_LOGS);
            copy(BlockTags.SPRUCE_LOGS, ItemTags.SPRUCE_LOGS);
            copy(BlockTags.JUNGLE_LOGS, ItemTags.JUNGLE_LOGS);
            copy(BlockTags.ACACIA_LOGS, ItemTags.ACACIA_LOGS);
            copy(BlockTags.DARK_OAK_LOGS, ItemTags.DARK_OAK_LOGS);
            copy(BlockTags.MANGROVE_LOGS, ItemTags.MANGROVE_LOGS);
            copy(BlockTags.CHERRY_LOGS, ItemTags.CHERRY_LOGS);
            copy(BlockTags.CRIMSON_STEMS, ItemTags.CRIMSON_STEMS);
            copy(BlockTags.WARPED_STEMS, ItemTags.WARPED_STEMS);
            copy(BlockTags.BAMBOO_BLOCKS, ItemTags.BAMBOO_BLOCKS);
            copy(Tags.Blocks.STRIPPED_LOGS, Tags.Items.STRIPPED_LOGS);

            tag(ItemTags.NON_FLAMMABLE_WOOD).add(
                    ModBlocks.CRIMSON.thinBlock().get().asItem(),
                    ModBlocks.CRIMSON.strippedThinBlock().get().asItem(),
                    ModBlocks.WARPED.thinBlock().get().asItem(),
                    ModBlocks.WARPED.strippedThinBlock().get().asItem()
            );
        }
    }
}
