package de.artemis.thinlogs.common.data;

import de.artemis.thinlogs.ThinLogs;
import de.artemis.thinlogs.common.registration.ModBlocks;
import de.artemis.thinlogs.common.registration.ModTags;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class TagsProvider {
    public static class BlockTagsProvider extends net.minecraft.data.tags.TagsProvider<Block> {
        private DataGenerator generator;

        @SuppressWarnings("deprecation")
        protected BlockTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
            super(generator, Registry.BLOCK, ThinLogs.MOD_ID, existingFileHelper);
            this.generator = generator;
        }

        @Override
        protected void addTags() {
            tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.THIN_OAK_LOG.get(), ModBlocks.THIN_STRIPPED_OAK_LOG.get(), ModBlocks.THIN_BIRCH_LOG.get(), ModBlocks.THIN_STRIPPED_BIRCH_LOG.get(), ModBlocks.THIN_SPRUCE_LOG.get(), ModBlocks.THIN_STRIPPED_SPRUCE_LOG.get(), ModBlocks.THIN_DARK_OAK_LOG.get(), ModBlocks.THIN_STRIPPED_DARK_OAK_LOG.get(), ModBlocks.THIN_ACACIA_LOG.get(), ModBlocks.THIN_STRIPPED_ACACIA_LOG.get(), ModBlocks.THIN_JUNGLE_LOG.get(), ModBlocks.THIN_STRIPPED_JUNGLE_LOG.get(), ModBlocks.THIN_MANGROVE_LOG.get(), ModBlocks.THIN_STRIPPED_MANGROVE_LOG.get(),ModBlocks.THIN_CRIMSON_STEM.get(), ModBlocks.THIN_STRIPPED_CRIMSON_STEM.get(), ModBlocks.THIN_WARPED_STEM.get(), ModBlocks.THIN_STRIPPED_WARPED_STEM.get());
        }

        @NotNull
        @Override
        protected Path getPath(ResourceLocation location) {
            return this.generator.getOutputFolder().resolve("data/" + location.getNamespace() + "/tags/blocks/" + location.getPath() + ".json");
        }

        @NotNull
        @Override
        public String getName() {
            return "Block tags";
        }
    }

    public static class ItemTagsProvider extends net.minecraft.data.tags.TagsProvider<Item> {
        private DataGenerator generator;

        @SuppressWarnings("deprecation")
        protected ItemTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
            super(generator, Registry.ITEM, ThinLogs.MOD_ID, existingFileHelper);
            this.generator = generator;
        }

        @Override
        protected void addTags() {
            tag(ModTags.Item.CAN_BE_APPLIED_ON_THIN_LOGS).add(Blocks.MOSS_CARPET.asItem(), Blocks.WHITE_CARPET.asItem(), Blocks.ORANGE_CARPET.asItem(), Blocks.MAGENTA_CARPET.asItem(), Blocks.LIGHT_BLUE_CARPET.asItem(), Blocks.YELLOW_CARPET.asItem(), Blocks.LIME_CARPET.asItem(), Blocks.PINK_CARPET.asItem(), Blocks.GRAY_CARPET.asItem(), Blocks.LIGHT_GRAY_CARPET.asItem(), Blocks.CYAN_CARPET.asItem(), Blocks.PURPLE_CARPET.asItem(), Blocks.BLUE_CARPET.asItem(), Blocks.BROWN_CARPET.asItem(), Blocks.GREEN_CARPET.asItem(), Blocks.RED_CARPET.asItem(), Blocks.BLACK_CARPET.asItem(), Blocks.OAK_LEAVES.asItem(), Blocks.BIRCH_LEAVES.asItem(), Blocks.SPRUCE_LEAVES.asItem(), Blocks.DARK_OAK_LEAVES.asItem(), Blocks.ACACIA_LEAVES.asItem(), Blocks.JUNGLE_LEAVES.asItem(), Blocks.MANGROVE_LEAVES.asItem(), Blocks.AZALEA_LEAVES.asItem(), Blocks.FLOWERING_AZALEA_LEAVES.asItem(), Blocks.SNOW.asItem(), Blocks.SNOW_BLOCK.asItem());
        }

        @NotNull
        @Override
        protected Path getPath(ResourceLocation location) {
            return this.generator.getOutputFolder().resolve("data/" + location.getNamespace() + "/tags/items/" + location.getPath() + ".json");
        }

        @NotNull
        @Override
        public String getName() {
            return "Item tags";
        }
    }
}
