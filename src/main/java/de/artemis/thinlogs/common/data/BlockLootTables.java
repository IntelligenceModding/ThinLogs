package de.artemis.thinlogs.common.data;

import de.artemis.thinlogs.common.registration.ModBlocks;
import de.artemis.thinlogs.common.registration.Registration;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class BlockLootTables extends BlockLootSubProvider {

    protected BlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.THIN_OAK_LOG.get());
        dropSelf(ModBlocks.THIN_STRIPPED_OAK_LOG.get());
        dropSelf(ModBlocks.THIN_BIRCH_LOG.get());
        dropSelf(ModBlocks.THIN_STRIPPED_BIRCH_LOG.get());
        dropSelf(ModBlocks.THIN_SPRUCE_LOG.get());
        dropSelf(ModBlocks.THIN_STRIPPED_SPRUCE_LOG.get());
        dropSelf(ModBlocks.THIN_DARK_OAK_LOG.get());
        dropSelf(ModBlocks.THIN_STRIPPED_DARK_OAK_LOG.get());
        dropSelf(ModBlocks.THIN_ACACIA_LOG.get());
        dropSelf(ModBlocks.THIN_STRIPPED_ACACIA_LOG.get());
        dropSelf(ModBlocks.THIN_JUNGLE_LOG.get());
        dropSelf(ModBlocks.THIN_STRIPPED_JUNGLE_LOG.get());
        dropSelf(ModBlocks.THIN_MANGROVE_LOG.get());
        dropSelf(ModBlocks.THIN_STRIPPED_MANGROVE_LOG.get());
        dropSelf(ModBlocks.THIN_CRIMSON_STEM.get());
        dropSelf(ModBlocks.THIN_STRIPPED_CRIMSON_STEM.get());
        dropSelf(ModBlocks.THIN_WARPED_STEM.get());
        dropSelf(ModBlocks.THIN_STRIPPED_WARPED_STEM.get());
        dropSelf(ModBlocks.THIN_CHERRY_LOG.get());
        dropSelf(ModBlocks.THIN_STRIPPED_CHERRY_LOG.get());
        dropSelf(ModBlocks.THIN_BAMBOO_BLOCK.get());
        dropSelf(ModBlocks.THIN_STRIPPED_BAMBOO_BLOCK.get());
    }

    @Override
    @NotNull
    protected Iterable<Block> getKnownBlocks() {
        return Registration.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
