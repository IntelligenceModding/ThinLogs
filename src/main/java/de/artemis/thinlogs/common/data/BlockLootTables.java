package de.artemis.thinlogs.common.data;

import de.artemis.thinlogs.common.registration.ModBlocks;
import de.artemis.thinlogs.common.registration.Registration;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlockLootTables extends BlockLoot {

    @Override
    protected void addTables() {
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
    }

    @Override
    @NotNull
    protected Iterable<Block> getKnownBlocks() {
        return Registration.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
