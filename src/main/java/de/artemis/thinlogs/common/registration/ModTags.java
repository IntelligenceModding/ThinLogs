package de.artemis.thinlogs.common.registration;

import de.artemis.thinlogs.ThinLogs;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class ModTags {
    private ModTags() {
    }

    public static final class BlockTagsSet {
        public static final TagKey<Block> THIN_LOG_OVERLAY_LEAVES = blockTag("thin_log_overlay_leaves");
        public static final TagKey<Block> THIN_LOG_OVERLAY_CARPETS = blockTag("thin_log_overlay_carpets");
        public static final TagKey<Block> THIN_LOG_OVERLAY_SNOW = blockTag("thin_log_overlay_snow");

        private BlockTagsSet() {
        }

        private static TagKey<Block> blockTag(String name) {
            return BlockTags.create(Identifier.fromNamespaceAndPath(ThinLogs.MOD_ID, name));
        }
    }
}
