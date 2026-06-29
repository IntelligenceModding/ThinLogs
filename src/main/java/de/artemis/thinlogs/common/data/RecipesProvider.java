package de.artemis.thinlogs.common.data;

import de.artemis.thinlogs.common.registration.ModBlocks;
import de.artemis.thinlogs.common.registration.ThinLogSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class RecipesProvider extends RecipeProvider implements IConditionBuilder {
    public RecipesProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ModBlocks.allSets().forEach(set -> thinLogRecipes(recipeOutput, set));
    }

    private static void thinLogRecipes(RecipeOutput recipeOutput, ThinLogSet set) {
        ItemLike baseBlock = set.definition().baseBlock().get();
        ItemLike strippedBaseBlock = set.definition().strippedBaseBlock().get();
        ItemLike plankBlock = set.definition().plankBlock().get();
        String baseName = BuiltInRegistries.ITEM.getKey(plankBlock.asItem()).getPath().replace("_planks", "");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, set.thinBlock().get(), 8)
                .define('A', baseBlock)
                .pattern("A")
                .pattern("A")
                .unlockedBy("has_" + baseName, has(baseBlock))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, set.strippedThinBlock().get(), 8)
                .define('A', strippedBaseBlock)
                .pattern("A")
                .pattern("A")
                .unlockedBy("has_stripped_" + baseName, has(strippedBaseBlock))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, plankBlock)
                .requires(set.thinBlock().get())
                .unlockedBy("has_" + set.thinBlock().getId().getPath(), has(set.thinBlock().get()))
                .save(recipeOutput, ResourceLocation.withDefaultNamespace(baseName + "_planks_from_" + set.thinBlock().getId().getPath()));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, plankBlock)
                .requires(set.strippedThinBlock().get())
                .unlockedBy("has_" + set.strippedThinBlock().getId().getPath(), has(set.strippedThinBlock().get()))
                .save(recipeOutput, ResourceLocation.withDefaultNamespace(baseName + "_planks_from_" + set.strippedThinBlock().getId().getPath()));
    }
}
