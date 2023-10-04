package de.artemis.thinlogs.common.data;

import de.artemis.thinlogs.common.registration.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class RecipesProvider extends RecipeProvider implements IConditionBuilder {

    public RecipesProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_OAK_LOG.get(), 8).define('A', Blocks.OAK_LOG).pattern("A").pattern("A").unlockedBy("has_oak_log", has(Blocks.OAK_LOG)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_STRIPPED_OAK_LOG.get(), 8).define('A', Blocks.STRIPPED_OAK_LOG).pattern("A").pattern("A").unlockedBy("has_stripped_oak_log", has(Blocks.STRIPPED_OAK_LOG)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.OAK_PLANKS, 1).requires(ModBlocks.THIN_OAK_LOG.get()).unlockedBy("has_thin_oak_log", has(ModBlocks.THIN_OAK_LOG.get())).save(consumer, "oak_planks_from_thin_oak_log");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.OAK_PLANKS, 1).requires(ModBlocks.THIN_STRIPPED_OAK_LOG.get()).unlockedBy("has_thin_stripped_oak_log", has(ModBlocks.THIN_STRIPPED_OAK_LOG.get())).save(consumer, "oak_planks_from_thin_stripped_oak_log");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_BIRCH_LOG.get(), 8).define('A', Blocks.BIRCH_LOG).pattern("A").pattern("A").unlockedBy("has_birch_log", has(Blocks.BIRCH_LOG)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_STRIPPED_BIRCH_LOG.get(), 8).define('A', Blocks.STRIPPED_BIRCH_LOG).pattern("A").pattern("A").unlockedBy("has_stripped_birch_log", has(Blocks.STRIPPED_BIRCH_LOG)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.BIRCH_PLANKS, 1).requires(ModBlocks.THIN_BIRCH_LOG.get()).unlockedBy("has_thin_birch_log", has(ModBlocks.THIN_BIRCH_LOG.get())).save(consumer, "birch_planks_from_thin_birch_log");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.BIRCH_PLANKS, 1).requires(ModBlocks.THIN_STRIPPED_BIRCH_LOG.get()).unlockedBy("has_thin_stripped_birch_log", has(ModBlocks.THIN_STRIPPED_BIRCH_LOG.get())).save(consumer, "birch_planks_from_thin_stripped_birch_log");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_SPRUCE_LOG.get(), 8).define('A', Blocks.SPRUCE_LOG).pattern("A").pattern("A").unlockedBy("has_spruce_log", has(Blocks.SPRUCE_LOG)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_STRIPPED_SPRUCE_LOG.get(), 8).define('A', Blocks.STRIPPED_SPRUCE_LOG).pattern("A").pattern("A").unlockedBy("has_stripped_spruce_log", has(Blocks.STRIPPED_SPRUCE_LOG)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.SPRUCE_PLANKS, 1).requires(ModBlocks.THIN_SPRUCE_LOG.get()).unlockedBy("has_thin_spruce_log", has(ModBlocks.THIN_SPRUCE_LOG.get())).save(consumer, "spruce_planks_from_thin_spruce_log");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.SPRUCE_PLANKS, 1).requires(ModBlocks.THIN_STRIPPED_SPRUCE_LOG.get()).unlockedBy("has_thin_stripped_spruce_log", has(ModBlocks.THIN_STRIPPED_SPRUCE_LOG.get())).save(consumer, "spruce_planks_from_thin_stripped_spruce_log");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_DARK_OAK_LOG.get(), 8).define('A', Blocks.DARK_OAK_LOG).pattern("A").pattern("A").unlockedBy("has_dark_oak_log", has(Blocks.DARK_OAK_LOG)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_STRIPPED_DARK_OAK_LOG.get(), 8).define('A', Blocks.STRIPPED_DARK_OAK_LOG).pattern("A").pattern("A").unlockedBy("has_stripped_dark_oak_log", has(Blocks.STRIPPED_DARK_OAK_LOG)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.DARK_OAK_PLANKS, 1).requires(ModBlocks.THIN_DARK_OAK_LOG.get()).unlockedBy("has_thin_dark_oak_log", has(ModBlocks.THIN_DARK_OAK_LOG.get())).save(consumer, "dark_oak_planks_from_thin_dark_oak_log");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.DARK_OAK_PLANKS, 1).requires(ModBlocks.THIN_STRIPPED_DARK_OAK_LOG.get()).unlockedBy("has_thin_stripped_dark_oak_log", has(ModBlocks.THIN_STRIPPED_DARK_OAK_LOG.get())).save(consumer, "dark_oak_planks_from_thin_stripped_dark_oak_log");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_ACACIA_LOG.get(), 8).define('A', Blocks.ACACIA_LOG).pattern("A").pattern("A").unlockedBy("has_acacia_log", has(Blocks.ACACIA_LOG)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_STRIPPED_ACACIA_LOG.get(), 8).define('A', Blocks.STRIPPED_ACACIA_LOG).pattern("A").pattern("A").unlockedBy("has_stripped_acacia_log", has(Blocks.STRIPPED_ACACIA_LOG)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.ACACIA_PLANKS, 1).requires(ModBlocks.THIN_ACACIA_LOG.get()).unlockedBy("has_thin_acacia_log", has(ModBlocks.THIN_ACACIA_LOG.get())).save(consumer, "acacia_planks_from_thin_acacia_log");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.ACACIA_PLANKS, 1).requires(ModBlocks.THIN_STRIPPED_ACACIA_LOG.get()).unlockedBy("has_thin_stripped_acacia_log", has(ModBlocks.THIN_STRIPPED_ACACIA_LOG.get())).save(consumer, "acacia_planks_from_thin_stripped_acacia_log");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_JUNGLE_LOG.get(), 8).define('A', Blocks.JUNGLE_LOG).pattern("A").pattern("A").unlockedBy("has_jungle_log", has(Blocks.JUNGLE_LOG)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_STRIPPED_JUNGLE_LOG.get(), 8).define('A', Blocks.STRIPPED_JUNGLE_LOG).pattern("A").pattern("A").unlockedBy("has_stripped_jungle_log", has(Blocks.STRIPPED_JUNGLE_LOG)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.JUNGLE_PLANKS, 1).requires(ModBlocks.THIN_JUNGLE_LOG.get()).unlockedBy("has_thin_jungle_log", has(ModBlocks.THIN_JUNGLE_LOG.get())).save(consumer, "jungle_planks_from_thin_jungle_log");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.JUNGLE_PLANKS, 1).requires(ModBlocks.THIN_STRIPPED_JUNGLE_LOG.get()).unlockedBy("has_thin_stripped_jungle_log", has(ModBlocks.THIN_STRIPPED_JUNGLE_LOG.get())).save(consumer, "jungle_planks_from_thin_stripped_jungle_log");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_MANGROVE_LOG.get(), 8).define('A', Blocks.MANGROVE_LOG).pattern("A").pattern("A").unlockedBy("has_mangrove_log", has(Blocks.MANGROVE_LOG)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_STRIPPED_MANGROVE_LOG.get(), 8).define('A', Blocks.STRIPPED_MANGROVE_LOG).pattern("A").pattern("A").unlockedBy("has_stripped_mangrove_log", has(Blocks.STRIPPED_MANGROVE_LOG)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.MANGROVE_PLANKS, 1).requires(ModBlocks.THIN_MANGROVE_LOG.get()).unlockedBy("has_thin_mangrove_log", has(ModBlocks.THIN_MANGROVE_LOG.get())).save(consumer, "mangrove_planks_from_thin_mangrove_log");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.MANGROVE_PLANKS, 1).requires(ModBlocks.THIN_STRIPPED_MANGROVE_LOG.get()).unlockedBy("has_thin_stripped_mangrove_log", has(ModBlocks.THIN_STRIPPED_MANGROVE_LOG.get())).save(consumer, "mangrove_planks_from_thin_stripped_mangrove_log");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_CRIMSON_STEM.get(), 8).define('A', Blocks.CRIMSON_STEM).pattern("A").pattern("A").unlockedBy("has_crimson_stem", has(Blocks.CRIMSON_STEM)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_STRIPPED_CRIMSON_STEM.get(), 8).define('A', Blocks.STRIPPED_CRIMSON_STEM).pattern("A").pattern("A").unlockedBy("has_stripped_crimson_stem", has(Blocks.STRIPPED_CRIMSON_STEM)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.CRIMSON_PLANKS, 1).requires(ModBlocks.THIN_CRIMSON_STEM.get()).unlockedBy("has_thin_crimson_stem", has(ModBlocks.THIN_CRIMSON_STEM.get())).save(consumer, "crimson_planks_from_thin_crimson_stem");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.CRIMSON_PLANKS, 1).requires(ModBlocks.THIN_STRIPPED_CRIMSON_STEM.get()).unlockedBy("has_thin_stripped_crimson_stem", has(ModBlocks.THIN_STRIPPED_CRIMSON_STEM.get())).save(consumer, "crimson_planks_from_thin_stripped_crimson_stem");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_WARPED_STEM.get(), 8).define('A', Blocks.WARPED_STEM).pattern("A").pattern("A").unlockedBy("has_warped_stem", has(Blocks.WARPED_STEM)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_STRIPPED_WARPED_STEM.get(), 8).define('A', Blocks.STRIPPED_WARPED_STEM).pattern("A").pattern("A").unlockedBy("has_stripped_warped_stem", has(Blocks.STRIPPED_WARPED_STEM)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.WARPED_PLANKS, 1).requires(ModBlocks.THIN_WARPED_STEM.get()).unlockedBy("has_thin_warped_stem", has(ModBlocks.THIN_WARPED_STEM.get())).save(consumer, "warped_planks_from_thin_warped_stem");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.WARPED_PLANKS, 1).requires(ModBlocks.THIN_STRIPPED_WARPED_STEM.get()).unlockedBy("has_thin_stripped_warped_stem", has(ModBlocks.THIN_STRIPPED_WARPED_STEM.get())).save(consumer, "warped_planks_from_thin_stripped_warped_stem");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_CHERRY_LOG.get(), 8).define('A', Blocks.CHERRY_LOG).pattern("A").pattern("A").unlockedBy("has_cherry_log", has(Blocks.CHERRY_LOG)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_STRIPPED_CHERRY_LOG.get(), 8).define('A', Blocks.STRIPPED_CHERRY_LOG).pattern("A").pattern("A").unlockedBy("has_stripped_cherry_log", has(Blocks.STRIPPED_CHERRY_LOG)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.CHERRY_PLANKS, 1).requires(ModBlocks.THIN_CHERRY_LOG.get()).unlockedBy("has_thin_cherry_log", has(ModBlocks.THIN_CHERRY_LOG.get())).save(consumer, "cherry_planks_from_thin_cherry_log");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.CHERRY_PLANKS, 1).requires(ModBlocks.THIN_STRIPPED_CHERRY_LOG.get()).unlockedBy("has_thin_stripped_cherry_log", has(ModBlocks.THIN_STRIPPED_CHERRY_LOG.get())).save(consumer, "cherry_planks_from_thin_stripped_cherry_log");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_BAMBOO_BLOCK.get(), 8).define('A', Blocks.BAMBOO_BLOCK).pattern("A").pattern("A").unlockedBy("has_bamboo_block", has(Blocks.BAMBOO_BLOCK)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THIN_STRIPPED_BAMBOO_BLOCK.get(), 8).define('A', Blocks.STRIPPED_BAMBOO_BLOCK).pattern("A").pattern("A").unlockedBy("has_stripped_bamboo_block", has(Blocks.STRIPPED_BAMBOO_BLOCK)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.BAMBOO_PLANKS, 1).requires(ModBlocks.THIN_BAMBOO_BLOCK.get()).unlockedBy("has_thin_bamboo_block", has(ModBlocks.THIN_BAMBOO_BLOCK.get())).save(consumer, "bamboo_planks_from_thin_bamboo_block");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.BAMBOO_PLANKS, 1).requires(ModBlocks.THIN_STRIPPED_BAMBOO_BLOCK.get()).unlockedBy("has_thin_stripped_bamboo_block", has(ModBlocks.THIN_STRIPPED_BAMBOO_BLOCK.get())).save(consumer, "bamboo_planks_from_thin_stripped_bamboo_block");
    }
}
