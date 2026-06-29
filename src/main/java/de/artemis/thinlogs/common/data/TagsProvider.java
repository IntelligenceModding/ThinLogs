package de.artemis.thinlogs.common.data;

import de.artemis.thinlogs.ThinLogs;
import de.artemis.thinlogs.common.registration.ModBlocks;
import de.artemis.thinlogs.common.registration.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public final class TagsProvider {
    private TagsProvider() {
    }

    public static class BlockTagsProvider extends net.minecraft.data.tags.TagsProvider<Block> {
        private final PackOutput packOutput;

        protected BlockTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper existingFileHelper) {
            super(packOutput, Registries.BLOCK, future, ThinLogs.MOD_ID, existingFileHelper);
            this.packOutput = packOutput;
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            for (Block block : ModBlocks.allThinLogBlocks()) {
                tag(BlockTags.MINEABLE_WITH_AXE).add(key(block));
            }

            tag(ModTags.BlockTagsSet.THIN_LOG_OVERLAY_LEAVES).addTag(BlockTags.LEAVES);
            tag(ModTags.BlockTagsSet.THIN_LOG_OVERLAY_CARPETS)
                    .addTag(BlockTags.WOOL_CARPETS)
                    .add(key(Blocks.MOSS_CARPET));
            tag(ModTags.BlockTagsSet.THIN_LOG_OVERLAY_SNOW)
                    .add(key(Blocks.SNOW), key(Blocks.SNOW_BLOCK));
        }

        @Override
        protected @NotNull Path getPath(ResourceLocation location) {
            return packOutput.getOutputFolder().resolve("data/" + location.getNamespace() + "/tags/blocks/" + location.getPath() + ".json");
        }

        @Override
        public @NotNull String getName() {
            return "Block tags";
        }

        private ResourceKey<Block> key(Block block) {
            return BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow();
        }
    }
}
