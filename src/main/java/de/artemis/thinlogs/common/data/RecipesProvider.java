package de.artemis.thinlogs.common.data;

import de.artemis.thinlogs.common.registration.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.concurrent.CompletableFuture;

public class RecipesProvider extends RecipeProvider implements IConditionBuilder {
    public RecipesProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        thinLogRecipes(recipeOutput, ModBlocks.THIN_OAK_LOG, ModBlocks.THIN_STRIPPED_OAK_LOG, Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG, Blocks.OAK_PLANKS, "oak");
        thinLogRecipes(recipeOutput, ModBlocks.THIN_BIRCH_LOG, ModBlocks.THIN_STRIPPED_BIRCH_LOG, Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_PLANKS, "birch");
        thinLogRecipes(recipeOutput, ModBlocks.THIN_SPRUCE_LOG, ModBlocks.THIN_STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_PLANKS, "spruce");
        thinLogRecipes(recipeOutput, ModBlocks.THIN_DARK_OAK_LOG, ModBlocks.THIN_STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS, "dark_oak");
        thinLogRecipes(recipeOutput, ModBlocks.THIN_ACACIA_LOG, ModBlocks.THIN_STRIPPED_ACACIA_LOG, Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_PLANKS, "acacia");
        thinLogRecipes(recipeOutput, ModBlocks.THIN_JUNGLE_LOG, ModBlocks.THIN_STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_PLANKS, "jungle");
        thinLogRecipes(recipeOutput, ModBlocks.THIN_MANGROVE_LOG, ModBlocks.THIN_STRIPPED_MANGROVE_LOG, Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG, Blocks.MANGROVE_PLANKS, "mangrove");
        thinLogRecipes(recipeOutput, ModBlocks.THIN_CRIMSON_STEM, ModBlocks.THIN_STRIPPED_CRIMSON_STEM, Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM, Blocks.CRIMSON_PLANKS, "crimson");
        thinLogRecipes(recipeOutput, ModBlocks.THIN_WARPED_STEM, ModBlocks.THIN_STRIPPED_WARPED_STEM, Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM, Blocks.WARPED_PLANKS, "warped");
        thinLogRecipes(recipeOutput, ModBlocks.THIN_CHERRY_LOG, ModBlocks.THIN_STRIPPED_CHERRY_LOG, Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG, Blocks.CHERRY_PLANKS, "cherry");
        thinLogRecipes(recipeOutput, ModBlocks.THIN_BAMBOO_BLOCK, ModBlocks.THIN_STRIPPED_BAMBOO_BLOCK, Blocks.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK, Blocks.BAMBOO_PLANKS, "bamboo");
    }

    private static void thinLogRecipes(
            RecipeOutput recipeOutput,
            DeferredBlock<?> thinBlock,
            DeferredBlock<?> strippedThinBlock,
            ItemLike baseBlock,
            ItemLike strippedBaseBlock,
            ItemLike plankBlock,
            String baseName
    ) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, thinBlock.get(), 8)
                .define('A', baseBlock)
                .pattern("A")
                .pattern("A")
                .unlockedBy("has_" + baseName, has(baseBlock))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, strippedThinBlock.get(), 8)
                .define('A', strippedBaseBlock)
                .pattern("A")
                .pattern("A")
                .unlockedBy("has_stripped_" + baseName, has(strippedBaseBlock))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, plankBlock)
                .requires(thinBlock.get())
                .unlockedBy("has_thin_" + baseName, has(thinBlock.get()))
                .save(recipeOutput, ResourceLocation.withDefaultNamespace(baseName + "_planks_from_" + thinBlock.getId().getPath()));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, plankBlock)
                .requires(strippedThinBlock.get())
                .unlockedBy("has_thin_stripped_" + baseName, has(strippedThinBlock.get()))
                .save(recipeOutput, ResourceLocation.withDefaultNamespace(baseName + "_planks_from_" + strippedThinBlock.getId().getPath()));
    }
}
