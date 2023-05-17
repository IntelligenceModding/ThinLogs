package de.artemis.thinlogs.common.blockStateProperties;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum AppliedOnThinLogBlock implements StringRepresentable {
    DEFAULT("default", Blocks.AIR::asItem),

    MOSS_CARPET("moss_carpet", Blocks.MOSS_CARPET::asItem),
    WHITE_CARPET("white_carpet", Blocks.WHITE_CARPET::asItem),
    ORANGE_CARPET("orange_carpet", Blocks.ORANGE_CARPET::asItem),
    MAGENTA_CARPET("magenta_carpet", Blocks.MAGENTA_CARPET::asItem),
    LIGHT_BLUE_CARPET("light_blue_carpet", Blocks.LIGHT_BLUE_CARPET::asItem),
    YELLOW_CARPET("yellow_carpet", Blocks.YELLOW_CARPET::asItem),
    LIME_CARPET("lime_carpet", Blocks.LIME_CARPET::asItem),
    PINK_CARPET("pink_carpet", Blocks.PINK_CARPET::asItem),
    GRAY_CARPET("gray_carpet", Blocks.GRAY_CARPET::asItem),
    LIGHT_GRAY_CARPET("light_gray_carpet", Blocks.LIGHT_GRAY_CARPET::asItem),
    CYAN_CARPET("cyan_carpet", Blocks.CYAN_CARPET::asItem),
    PURPLE_CARPET("purple_carpet", Blocks.PURPLE_CARPET::asItem),
    BLUE_CARPET("blue_carpet", Blocks.BLUE_CARPET::asItem),
    BROWN_CARPET("brown_carpet", Blocks.BROWN_CARPET::asItem),
    GREEN_CARPET("green_carpet", Blocks.GREEN_CARPET::asItem),
    RED_CARPET("red_carpet", Blocks.RED_CARPET::asItem),
    BLACK_CARPET("black_carpet", Blocks.BLACK_CARPET::asItem),
    OAK_LEAVES("oak_leaves", Blocks.OAK_LEAVES::asItem),
    BIRCH_LEAVES("birch_leaves", Blocks.BIRCH_LEAVES::asItem),
    SPRUCE_LEAVES("spruce_leaves", Blocks.SPRUCE_LEAVES::asItem),
    DARK_OAK_LEAVES("dark_oak_leaves", Blocks.DARK_OAK_LEAVES::asItem),
    ACACIA_LEAVES("acacia_leaves", Blocks.ACACIA_LEAVES::asItem),
    JUNGLE_LEAVES("jungle_leaves", Blocks.JUNGLE_LEAVES::asItem),
    MANGROVE_LEAVES("mangrove_leaves", Blocks.JUNGLE_LEAVES::asItem),
    AZALEA_LEAVES("azalea_leaves", Blocks.AZALEA_LEAVES::asItem),
    FLOWERING_AZALEA_LEAVES("flowering_azalea_leaves", Blocks.FLOWERING_AZALEA_LEAVES::asItem),

    SNOW_LAYER_1("snow_layer_1", Blocks.SNOW::asItem),
    SNOW_LAYER_2("snow_layer_2", Blocks.SNOW::asItem),
    SNOW_LAYER_3("snow_layer_3", Blocks.SNOW::asItem),
    SNOW_LAYER_4("snow_layer_4", Blocks.SNOW::asItem),
    SNOW_LAYER_5("snow_layer_5", Blocks.SNOW::asItem),
    SNOW_LAYER_6("snow_layer_6", Blocks.SNOW::asItem),
    SNOW_LAYER_7("snow_layer_7", Blocks.SNOW::asItem),
    SNOW_LAYER_8("snow_layer_8", Blocks.SNOW::asItem),
    SNOW_BLOCK("snow_block", Blocks.SNOW_BLOCK::asItem);

    private final String name;
    private final Supplier<Item> item;

    AppliedOnThinLogBlock(String string, Supplier<Item> item) {
        this.name = string;
        this.item = item;
    }

    public String toString() {
        return this.name;
    }

    public Supplier<Item> getItem() {
        return item;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}
