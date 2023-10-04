package de.artemis.thinlogs.common.data;

import de.artemis.thinlogs.ThinLogs;
import de.artemis.thinlogs.common.registration.ModBlocks;
import de.artemis.thinlogs.common.registration.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class TagsProvider {
    public static class BlockTagsProvider extends net.minecraft.data.tags.TagsProvider<Block> {
        private PackOutput packOutput;

        protected BlockTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper existingFileHelper) {
            super(packOutput, Registries.BLOCK, future, ThinLogs.MOD_ID, existingFileHelper);
            this.packOutput = packOutput;
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            tag(BlockTags.MINEABLE_WITH_AXE).add(getKey(ModBlocks.THIN_OAK_LOG.get()), getKey(ModBlocks.THIN_STRIPPED_OAK_LOG.get()), getKey(ModBlocks.THIN_BIRCH_LOG.get()), getKey(ModBlocks.THIN_STRIPPED_BIRCH_LOG.get()), getKey(ModBlocks.THIN_SPRUCE_LOG.get()), getKey(ModBlocks.THIN_STRIPPED_SPRUCE_LOG.get()), getKey(ModBlocks.THIN_DARK_OAK_LOG.get()), getKey(ModBlocks.THIN_STRIPPED_DARK_OAK_LOG.get()), getKey(ModBlocks.THIN_ACACIA_LOG.get()), getKey(ModBlocks.THIN_STRIPPED_ACACIA_LOG.get()), getKey(ModBlocks.THIN_JUNGLE_LOG.get()), getKey(ModBlocks.THIN_STRIPPED_JUNGLE_LOG.get()), getKey(ModBlocks.THIN_MANGROVE_LOG.get()), getKey(ModBlocks.THIN_STRIPPED_MANGROVE_LOG.get()),getKey(ModBlocks.THIN_CRIMSON_STEM.get()), getKey(ModBlocks.THIN_STRIPPED_CRIMSON_STEM.get()), getKey(ModBlocks.THIN_WARPED_STEM.get()), getKey(ModBlocks.THIN_STRIPPED_WARPED_STEM.get()), getKey(ModBlocks.THIN_CHERRY_LOG.get()), getKey(ModBlocks.THIN_STRIPPED_CHERRY_LOG.get()), getKey(ModBlocks.THIN_BAMBOO_BLOCK.get()), getKey(ModBlocks.THIN_STRIPPED_BAMBOO_BLOCK.get()));
        }

        @NotNull
        @Override
        protected Path getPath(ResourceLocation location) {
            return this.packOutput.getOutputFolder().resolve("data/" + location.getNamespace() + "/tags/blocks/" + location.getPath() + ".json");
        }

        private ResourceKey<Block> getKey(Block block) {
            return ForgeRegistries.BLOCKS.getResourceKey(block).get();
        }

        @NotNull
        @Override
        public String getName() {
            return "Block tags";
        }
    }

    public static class ItemTagsProvider extends net.minecraft.data.tags.TagsProvider<Item> {
        private PackOutput packOutput;

        protected ItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper existingFileHelper) {
            super(packOutput, Registries.ITEM, future, ThinLogs.MOD_ID, existingFileHelper);
            this.packOutput = packOutput;
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            tag(ModTags.Item.CAN_BE_APPLIED_ON_THIN_LOGS).add(getKey(Blocks.MOSS_CARPET.asItem()), getKey(Blocks.WHITE_CARPET.asItem()), getKey(Blocks.ORANGE_CARPET.asItem()), getKey(Blocks.MAGENTA_CARPET.asItem()), getKey(Blocks.LIGHT_BLUE_CARPET.asItem()), getKey(Blocks.YELLOW_CARPET.asItem()), getKey(Blocks.LIME_CARPET.asItem()), getKey(Blocks.PINK_CARPET.asItem()), getKey(Blocks.GRAY_CARPET.asItem()), getKey(Blocks.LIGHT_GRAY_CARPET.asItem()), getKey(Blocks.CYAN_CARPET.asItem()), getKey(Blocks.PURPLE_CARPET.asItem()), getKey(Blocks.BLUE_CARPET.asItem()), getKey(Blocks.BROWN_CARPET.asItem()), getKey(Blocks.GREEN_CARPET.asItem()), getKey(Blocks.RED_CARPET.asItem()), getKey(Blocks.BLACK_CARPET.asItem()), getKey(Blocks.OAK_LEAVES.asItem()), getKey(Blocks.BIRCH_LEAVES.asItem()), getKey(Blocks.SPRUCE_LEAVES.asItem()), getKey(Blocks.DARK_OAK_LEAVES.asItem()), getKey(Blocks.ACACIA_LEAVES.asItem()), getKey(Blocks.JUNGLE_LEAVES.asItem()), getKey(Blocks.MANGROVE_LEAVES.asItem()), getKey(Blocks.AZALEA_LEAVES.asItem()), getKey(Blocks.FLOWERING_AZALEA_LEAVES.asItem()), getKey(Blocks.SNOW.asItem()), getKey(Blocks.SNOW_BLOCK.asItem()), getKey(Blocks.CHERRY_LEAVES.asItem()));
        }

        @NotNull
        @Override
        protected Path getPath(ResourceLocation location) {
            return this.packOutput.getOutputFolder().resolve("data/" + location.getNamespace() + "/tags/items/" + location.getPath() + ".json");
        }

        private ResourceKey<Item> getKey(Item item) {
            return ForgeRegistries.ITEMS.getResourceKey(item).get();
        }

        @NotNull
        @Override
        public String getName() {
            return "Item tags";
        }
    }
}
