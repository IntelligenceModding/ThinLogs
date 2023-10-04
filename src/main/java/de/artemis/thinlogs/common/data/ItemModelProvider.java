package de.artemis.thinlogs.common.data;

import de.artemis.thinlogs.ThinLogs;
import de.artemis.thinlogs.common.registration.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {

    public ItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, ThinLogs.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        thinLogBlock(ModBlocks.THIN_OAK_LOG.get(), new ResourceLocation("block/oak_log"), new ResourceLocation("block/oak_log_top"));
        thinLogBlock(ModBlocks.THIN_STRIPPED_OAK_LOG.get(), new ResourceLocation("block/stripped_oak_log"), new ResourceLocation("block/stripped_oak_log_top"));
        thinLogBlock(ModBlocks.THIN_BIRCH_LOG.get(), new ResourceLocation("block/birch_log"), new ResourceLocation("block/birch_log_top"));
        thinLogBlock(ModBlocks.THIN_STRIPPED_BIRCH_LOG.get(), new ResourceLocation("block/stripped_birch_log"), new ResourceLocation("block/stripped_birch_log_top"));
        thinLogBlock(ModBlocks.THIN_SPRUCE_LOG.get(), new ResourceLocation("block/spruce_log"), new ResourceLocation("block/spruce_log_top"));
        thinLogBlock(ModBlocks.THIN_STRIPPED_SPRUCE_LOG.get(), new ResourceLocation("block/stripped_spruce_log"), new ResourceLocation("block/stripped_spruce_log_top"));
        thinLogBlock(ModBlocks.THIN_DARK_OAK_LOG.get(), new ResourceLocation("block/dark_oak_log"), new ResourceLocation("block/dark_oak_log_top"));
        thinLogBlock(ModBlocks.THIN_STRIPPED_DARK_OAK_LOG.get(), new ResourceLocation("block/stripped_dark_oak_log"), new ResourceLocation("block/stripped_dark_oak_log_top"));
        thinLogBlock(ModBlocks.THIN_ACACIA_LOG.get(), new ResourceLocation("block/acacia_log"), new ResourceLocation("block/acacia_log_top"));
        thinLogBlock(ModBlocks.THIN_STRIPPED_ACACIA_LOG.get(), new ResourceLocation("block/stripped_acacia_log"), new ResourceLocation("block/stripped_acacia_log_top"));
        thinLogBlock(ModBlocks.THIN_JUNGLE_LOG.get(), new ResourceLocation("block/jungle_log"), new ResourceLocation("block/jungle_log_top"));
        thinLogBlock(ModBlocks.THIN_STRIPPED_JUNGLE_LOG.get(), new ResourceLocation("block/stripped_jungle_log"), new ResourceLocation("block/stripped_jungle_log_top"));
        thinLogBlock(ModBlocks.THIN_MANGROVE_LOG.get(), new ResourceLocation("block/mangrove_log"), new ResourceLocation("block/mangrove_log_top"));
        thinLogBlock(ModBlocks.THIN_STRIPPED_MANGROVE_LOG.get(), new ResourceLocation("block/stripped_mangrove_log"), new ResourceLocation("block/stripped_mangrove_log_top"));
        thinLogBlock(ModBlocks.THIN_CRIMSON_STEM.get(), new ResourceLocation("block/crimson_stem"), new ResourceLocation("block/crimson_stem_top"));
        thinLogBlock(ModBlocks.THIN_STRIPPED_CRIMSON_STEM.get(), new ResourceLocation("block/stripped_crimson_stem"), new ResourceLocation("block/stripped_crimson_stem_top"));
        thinLogBlock(ModBlocks.THIN_WARPED_STEM.get(), new ResourceLocation("block/warped_stem"), new ResourceLocation("block/warped_stem_top"));
        thinLogBlock(ModBlocks.THIN_STRIPPED_WARPED_STEM.get(), new ResourceLocation("block/stripped_warped_stem"), new ResourceLocation("block/stripped_warped_stem_top"));
        thinLogBlock(ModBlocks.THIN_CHERRY_LOG.get(), new ResourceLocation("block/cherry_log"), new ResourceLocation("block/cherry_log_top"));
        thinLogBlock(ModBlocks.THIN_STRIPPED_CHERRY_LOG.get(), new ResourceLocation("block/stripped_cherry_log"), new ResourceLocation("block/stripped_cherry_log_top"));
        thinLogBlock(ModBlocks.THIN_BAMBOO_BLOCK.get(), new ResourceLocation("block/bamboo_block"), new ResourceLocation("block/bamboo_block_top"));
        thinLogBlock(ModBlocks.THIN_STRIPPED_BAMBOO_BLOCK.get(), new ResourceLocation("block/stripped_bamboo_block"), new ResourceLocation("block/stripped_bamboo_block_top"));
    }

    public void thinLogBlock(Block block, ResourceLocation texture_side, ResourceLocation texture_top) {
        withExistingParent(DataProvider.getRegistryName(block.asItem()), new ResourceLocation(ThinLogs.MOD_ID, "generation/thin_log_horizontal")).texture("log_side", texture_side).texture("log_top", texture_top);
    }

}
