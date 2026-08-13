package de.artemis.thinlogs.common.registration;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public record ThinLogSetDefinition(
        String thinId,
        String strippedThinId,
        String displayName,
        Supplier<Block> baseBlock,
        Supplier<Block> strippedBaseBlock,
        Supplier<Block> plankBlock,
        Identifier sideTexture,
        Identifier endTexture,
        Identifier strippedSideTexture,
        Identifier strippedEndTexture,
        SoundType soundType,
        boolean ignitedByLava
) {
    public static ThinLogSetDefinition log(String woodName, Supplier<Block> baseBlock, Supplier<Block> strippedBaseBlock, Supplier<Block> plankBlock) {
        return new ThinLogSetDefinition(
                "thin_" + woodName + "_log",
                "thin_stripped_" + woodName + "_log",
                humanize(woodName) + " Log",
                baseBlock,
                strippedBaseBlock,
                plankBlock,
                Identifier.withDefaultNamespace("block/" + woodName + "_log"),
                Identifier.withDefaultNamespace("block/" + woodName + "_log_top"),
                Identifier.withDefaultNamespace("block/stripped_" + woodName + "_log"),
                Identifier.withDefaultNamespace("block/stripped_" + woodName + "_log_top"),
                SoundType.WOOD,
                true
        );
    }

    public static ThinLogSetDefinition stem(String stemName, Supplier<Block> baseBlock, Supplier<Block> strippedBaseBlock, Supplier<Block> plankBlock) {
        return new ThinLogSetDefinition(
                "thin_" + stemName + "_stem",
                "thin_stripped_" + stemName + "_stem",
                humanize(stemName) + " Stem",
                baseBlock,
                strippedBaseBlock,
                plankBlock,
                Identifier.withDefaultNamespace("block/" + stemName + "_stem"),
                Identifier.withDefaultNamespace("block/" + stemName + "_stem_top"),
                Identifier.withDefaultNamespace("block/stripped_" + stemName + "_stem"),
                Identifier.withDefaultNamespace("block/stripped_" + stemName + "_stem_top"),
                SoundType.WOOD,
                false
        );
    }

    public static ThinLogSetDefinition bamboo(Supplier<Block> baseBlock, Supplier<Block> strippedBaseBlock, Supplier<Block> plankBlock) {
        return new ThinLogSetDefinition(
                "thin_bamboo_block",
                "thin_stripped_bamboo_log",
                "Bamboo Block",
                baseBlock,
                strippedBaseBlock,
                plankBlock,
                Identifier.withDefaultNamespace("block/bamboo_block"),
                Identifier.withDefaultNamespace("block/bamboo_block_top"),
                Identifier.withDefaultNamespace("block/stripped_bamboo_block"),
                Identifier.withDefaultNamespace("block/stripped_bamboo_block_top"),
                SoundType.BAMBOO_WOOD,
                true
        );
    }

    public String strippedDisplayName() {
        return "Stripped " + displayName;
    }

    public BlockBehaviour.Properties createProperties() {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
                .strength(2.0F)
                .sound(soundType);
        return ignitedByLava ? properties.ignitedByLava() : properties;
    }

    private static String humanize(String name) {
        String[] parts = name.split("_");
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            if (!builder.isEmpty()) {
                builder.append(' ');
            }
            builder.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
        }
        return builder.toString();
    }
}
