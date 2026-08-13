package de.artemis.thinlogs.common.data;

import de.artemis.thinlogs.ThinLogs;
import de.artemis.thinlogs.common.registration.ModBlocks;
import de.artemis.thinlogs.common.registration.ModTags;
import de.artemis.thinlogs.common.registration.ThinLogSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
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
                tag(BlockTags.MINEABLE_WITH_AXE).add(key(block));
            }

            addSpeciesTags();
            addMinecraftLogTags();
            addCompatibilityTags();

            tag(ModTags.BlockTagsSet.THIN_LOG_OVERLAY_LEAVES).addTag(BlockTags.LEAVES);
            tag(ModTags.BlockTagsSet.THIN_LOG_OVERLAY_CARPETS)
                    .addTag(BlockTags.WOOL_CARPETS)
                    .add(key(Blocks.MOSS_CARPET));
            tag(ModTags.BlockTagsSet.THIN_LOG_OVERLAY_SNOW)
                    .addAll(List.of(key(Blocks.SNOW), key(Blocks.SNOW_BLOCK)));
        }

        private void addSpeciesTags() {
            tag(vanillaTag("oak_logs")).addAll(keys(ModBlocks.OAK));
            tag(vanillaTag("birch_logs")).addAll(keys(ModBlocks.BIRCH));
            tag(vanillaTag("spruce_logs")).addAll(keys(ModBlocks.SPRUCE));
            tag(vanillaTag("jungle_logs")).addAll(keys(ModBlocks.JUNGLE));
            tag(vanillaTag("acacia_logs")).addAll(keys(ModBlocks.ACACIA));
            tag(vanillaTag("dark_oak_logs")).addAll(keys(ModBlocks.DARK_OAK));
            tag(vanillaTag("pale_oak_logs")).addAll(keys(ModBlocks.PALE_OAK));
            tag(vanillaTag("mangrove_logs")).addAll(keys(ModBlocks.MANGROVE));
            tag(vanillaTag("cherry_logs")).addAll(keys(ModBlocks.CHERRY));
            tag(vanillaTag("crimson_stems")).addAll(keys(ModBlocks.CRIMSON));
            tag(vanillaTag("warped_stems")).addAll(keys(ModBlocks.WARPED));
            tag(BlockTags.BAMBOO_BLOCKS).addAll(keys(ModBlocks.BAMBOO));

            tag(BlockTags.OVERWORLD_NATURAL_LOGS).addAll(thinBlockKeys(OVERWORLD_NATURAL_LOG_SETS));
        }

        private void addMinecraftLogTags() {
            tag(BlockTags.LOGS).addAll(keys(ALL_LOG_SETS));
            tag(vanillaTag("logs_that_burn")).addAll(keys(FLAMMABLE_LOG_SETS));
            tag(BlockTags.COMPLETES_FIND_TREE_TUTORIAL).addAll(keys(ALL_LOG_SETS));
        }

        private void addCompatibilityTags() {
            tag(Tags.Blocks.OVERWORLD_NATURAL_LOGS).addAll(thinBlockKeys(OVERWORLD_NATURAL_LOG_SETS));
            tag(Tags.Blocks.NETHER_NATURAL_LOGS).addAll(thinBlockKeys(NETHER_NATURAL_LOG_SETS));
            tag(Tags.Blocks.NATURAL_LOGS).addAll(thinBlockKeys(NATURAL_LOG_SETS));

            tag(Tags.Blocks.STRIPPED_LOGS).addAll(List.of(
                    key(ModBlocks.OAK.strippedThinBlock().get()),
                    key(ModBlocks.BIRCH.strippedThinBlock().get()),
                    key(ModBlocks.SPRUCE.strippedThinBlock().get()),
                    key(ModBlocks.JUNGLE.strippedThinBlock().get()),
                    key(ModBlocks.ACACIA.strippedThinBlock().get()),
                    key(ModBlocks.DARK_OAK.strippedThinBlock().get()),
                    key(ModBlocks.PALE_OAK.strippedThinBlock().get()),
                    key(ModBlocks.MANGROVE.strippedThinBlock().get()),
                    key(ModBlocks.CHERRY.strippedThinBlock().get()),
                    key(ModBlocks.BAMBOO.strippedThinBlock().get()),
                    key(ModBlocks.CRIMSON.strippedThinBlock().get()),
                    key(ModBlocks.WARPED.strippedThinBlock().get())
            ));
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

    private static List<ResourceKey<Block>> keys(ThinLogSet... sets) {
        List<ResourceKey<Block>> keys = new ArrayList<>(sets.length * 2);
        for (ThinLogSet set : sets) {
            keys.add(key(set.thinBlock().get()));
            keys.add(key(set.strippedThinBlock().get()));
        }
        return keys;
    }

    private static List<ResourceKey<Block>> thinBlockKeys(ThinLogSet... sets) {
        List<ResourceKey<Block>> keys = new ArrayList<>(sets.length);
        for (ThinLogSet set : sets) {
            keys.add(key(set.thinBlock().get()));
        }
        return keys;
    }

    private static ResourceKey<Block> key(Block block) {
        return block.builtInRegistryHolder().key();
    }

    private static TagKey<Block> vanillaTag(String name) {
        return TagKey.create(Registries.BLOCK, Identifier.withDefaultNamespace(name));
    }

    private static ThinLogSet[] concat(ThinLogSet[] first, ThinLogSet[] second) {
        ThinLogSet[] result = new ThinLogSet[first.length + second.length];
        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
