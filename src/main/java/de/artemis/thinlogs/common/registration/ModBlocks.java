package de.artemis.thinlogs.common.registration;

import de.artemis.thinlogs.ThinLogs;
import de.artemis.thinlogs.common.blocks.ThinLogBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = Registration.BLOCKS.register(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(toReturn.get(),
                new Item.Properties()));

        return toReturn;
    }

    public static void register() {
    }

    public static final RegistryObject<ThinLogBlock> THIN_OAK_LOG = register("thin_oak_log",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().ignitedByLava().strength(2.0F).sound(SoundType.WOOD), false));

    public static final RegistryObject<ThinLogBlock> THIN_STRIPPED_OAK_LOG = register("thin_stripped_oak_log",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().ignitedByLava().strength(2.0F).sound(SoundType.WOOD), true));

    public static final RegistryObject<ThinLogBlock> THIN_BIRCH_LOG = register("thin_birch_log",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().ignitedByLava().strength(2.0F).sound(SoundType.WOOD), false));

    public static final RegistryObject<ThinLogBlock> THIN_STRIPPED_BIRCH_LOG = register("thin_stripped_birch_log",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().ignitedByLava().strength(2.0F).sound(SoundType.WOOD), true));

    public static final RegistryObject<ThinLogBlock> THIN_SPRUCE_LOG = register("thin_spruce_log",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().ignitedByLava().strength(2.0F).sound(SoundType.WOOD), false));

    public static final RegistryObject<ThinLogBlock> THIN_STRIPPED_SPRUCE_LOG = register("thin_stripped_spruce_log",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().ignitedByLava().strength(2.0F).sound(SoundType.WOOD), true));

    public static final RegistryObject<ThinLogBlock> THIN_DARK_OAK_LOG = register("thin_dark_oak_log",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().ignitedByLava().strength(2.0F).sound(SoundType.WOOD), false));

    public static final RegistryObject<ThinLogBlock> THIN_STRIPPED_DARK_OAK_LOG = register("thin_stripped_dark_oak_log",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().ignitedByLava().strength(2.0F).sound(SoundType.WOOD), true));

    public static final RegistryObject<ThinLogBlock> THIN_ACACIA_LOG = register("thin_acacia_log",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().ignitedByLava().strength(2.0F).sound(SoundType.WOOD), false));

    public static final RegistryObject<ThinLogBlock> THIN_STRIPPED_ACACIA_LOG = register("thin_stripped_acacia_log",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().ignitedByLava().strength(2.0F).sound(SoundType.WOOD), true));

    public static final RegistryObject<ThinLogBlock> THIN_JUNGLE_LOG = register("thin_jungle_log",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().ignitedByLava().strength(2.0F).sound(SoundType.WOOD), false));

    public static final RegistryObject<ThinLogBlock> THIN_STRIPPED_JUNGLE_LOG = register("thin_stripped_jungle_log",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().ignitedByLava().strength(2.0F).sound(SoundType.WOOD), true));

    public static final RegistryObject<ThinLogBlock> THIN_MANGROVE_LOG = register("thin_mangrove_log",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().ignitedByLava().strength(2.0F).sound(SoundType.WOOD), false));

    public static final RegistryObject<ThinLogBlock> THIN_STRIPPED_MANGROVE_LOG = register("thin_stripped_mangrove_log",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().ignitedByLava().strength(2.0F).sound(SoundType.WOOD), true));

    public static final RegistryObject<ThinLogBlock> THIN_CHERRY_LOG = register("thin_cherry_log",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().ignitedByLava().strength(2.0F).sound(SoundType.WOOD), false));

    public static final RegistryObject<ThinLogBlock> THIN_STRIPPED_CHERRY_LOG = register("thin_stripped_cherry_log",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().ignitedByLava().strength(2.0F).sound(SoundType.WOOD), true));

    public static final RegistryObject<ThinLogBlock> THIN_BAMBOO_BLOCK = register("thin_bamboo_block",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().ignitedByLava().strength(2.0F).sound(SoundType.BAMBOO_WOOD), false));

    public static final RegistryObject<ThinLogBlock> THIN_STRIPPED_BAMBOO_BLOCK = register("thin_stripped_bamboo_log",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().ignitedByLava().strength(2.0F).sound(SoundType.BAMBOO_WOOD), true));

    public static final RegistryObject<ThinLogBlock> THIN_CRIMSON_STEM = register("thin_crimson_stem",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.WOOD), false));

    public static final RegistryObject<ThinLogBlock> THIN_STRIPPED_CRIMSON_STEM = register("thin_stripped_crimson_stem",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.WOOD), true));

    public static final RegistryObject<ThinLogBlock> THIN_WARPED_STEM = register("thin_warped_stem",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.WOOD), false));

    public static final RegistryObject<ThinLogBlock> THIN_STRIPPED_WARPED_STEM = register("thin_stripped_warped_stem",
            () -> new ThinLogBlock(BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.WOOD), true));

}
