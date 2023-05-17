package de.artemis.thinlogs.common.data;

import de.artemis.thinlogs.ThinLogs;
import de.artemis.thinlogs.common.registration.ModBlocks;
import net.minecraft.data.DataGenerator;

public class LanguageProvider extends net.minecraftforge.common.data.LanguageProvider {

    public LanguageProvider(DataGenerator gen, String locale) {
        super(gen, ThinLogs.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.thinlogs", "Artemis' Thin Logs");
        add(ModBlocks.THIN_OAK_LOG.get(), "Thin Oak Log");
        add(ModBlocks.THIN_STRIPPED_OAK_LOG.get(), "Thin Stripped Oak Log");
        add(ModBlocks.THIN_BIRCH_LOG.get(), "Thin Birch Log");
        add(ModBlocks.THIN_STRIPPED_BIRCH_LOG.get(), "Thin Stripped Birch Log");
        add(ModBlocks.THIN_SPRUCE_LOG.get(), "Thin Spruce Log");
        add(ModBlocks.THIN_STRIPPED_SPRUCE_LOG.get(), "Thin Stripped Spruce Log");
        add(ModBlocks.THIN_DARK_OAK_LOG.get(), "Thin Dark Oak Log");
        add(ModBlocks.THIN_STRIPPED_DARK_OAK_LOG.get(), "Thin Stripped Dark Oak Log");
        add(ModBlocks.THIN_ACACIA_LOG.get(), "Thin Acacia Log");
        add(ModBlocks.THIN_STRIPPED_ACACIA_LOG.get(), "Thin Stripped Acacia Log");
        add(ModBlocks.THIN_JUNGLE_LOG.get(), "Thin Jungle Log");
        add(ModBlocks.THIN_STRIPPED_JUNGLE_LOG.get(), "Thin Stripped Jungle Log");
        add(ModBlocks.THIN_MANGROVE_LOG.get(), "Thin Mangrove Log");
        add(ModBlocks.THIN_STRIPPED_MANGROVE_LOG.get(), "Thin Stripped Mangrove Log");
        add(ModBlocks.THIN_CRIMSON_STEM.get(), "Thin Crimson Stem");
        add(ModBlocks.THIN_STRIPPED_CRIMSON_STEM.get(), "Thin Stripped Crimson Stem");
        add(ModBlocks.THIN_WARPED_STEM.get(), "Thin Warped Stem");
        add(ModBlocks.THIN_STRIPPED_WARPED_STEM.get(), "Thin Stripped Warped Stem");
    }

}
