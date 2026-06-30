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

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RecipesProvider extends RecipeProvider implements IConditionBuilder {
    public RecipesProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        Set<ResourceLocation> generatedRecipeIds = new LinkedHashSet<>();
        ModBlocks.allSets().forEach(set -> thinLogRecipes(recipeOutput, set, generatedRecipeIds));
        validateRecipeCoverage(generatedRecipeIds);
    }

    private static void thinLogRecipes(RecipeOutput recipeOutput, ThinLogSet set, Set<ResourceLocation> generatedRecipeIds) {
        ItemLike baseBlock = set.definition().baseBlock().get();
        ItemLike strippedBaseBlock = set.definition().strippedBaseBlock().get();
        ItemLike plankBlock = set.definition().plankBlock().get();
        ResourceLocation thinRecipeId = set.thinBlock().getId();
        ResourceLocation strippedThinRecipeId = set.strippedThinBlock().getId();
        ResourceLocation thinToPlanksRecipeId = plankRecipeId(plankBlock, set.thinBlock().getId());
        ResourceLocation strippedThinToPlanksRecipeId = plankRecipeId(plankBlock, set.strippedThinBlock().getId());

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, set.thinBlock().get(), 8)
                .define('A', baseBlock)
                .pattern("A")
                .pattern("A")
                .unlockedBy("has_" + thinRecipeId.getPath(), has(baseBlock))
                .save(recipeOutput, registerRecipeId(generatedRecipeIds, thinRecipeId));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, set.strippedThinBlock().get(), 8)
                .define('A', strippedBaseBlock)
                .pattern("A")
                .pattern("A")
                .unlockedBy("has_" + strippedThinRecipeId.getPath(), has(strippedBaseBlock))
                .save(recipeOutput, registerRecipeId(generatedRecipeIds, strippedThinRecipeId));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, plankBlock)
                .requires(set.thinBlock().get())
                .unlockedBy("has_" + set.thinBlock().getId().getPath(), has(set.thinBlock().get()))
                .save(recipeOutput, registerRecipeId(generatedRecipeIds, thinToPlanksRecipeId));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, plankBlock)
                .requires(set.strippedThinBlock().get())
                .unlockedBy("has_" + set.strippedThinBlock().getId().getPath(), has(set.strippedThinBlock().get()))
                .save(recipeOutput, registerRecipeId(generatedRecipeIds, strippedThinToPlanksRecipeId));
    }

    private static ResourceLocation plankRecipeId(ItemLike plankBlock, ResourceLocation sourceId) {
        String plankName = BuiltInRegistries.ITEM.getKey(plankBlock.asItem()).getPath().replace("_planks", "");
        return ResourceLocation.withDefaultNamespace(plankName + "_planks_from_" + sourceId.getPath());
    }

    private static ResourceLocation registerRecipeId(Set<ResourceLocation> generatedRecipeIds, ResourceLocation recipeId) {
        if (!generatedRecipeIds.add(recipeId)) {
            throw new IllegalStateException("Duplicate recipe id generated: " + recipeId);
        }
        return recipeId;
    }

    private static void validateRecipeCoverage(Set<ResourceLocation> generatedRecipeIds) {
        Set<ResourceLocation> expectedRecipeIds = new LinkedHashSet<>();
        ModBlocks.allSets().forEach(set -> {
            expectedRecipeIds.add(set.thinBlock().getId());
            expectedRecipeIds.add(set.strippedThinBlock().getId());
            expectedRecipeIds.add(plankRecipeId(set.definition().plankBlock().get(), set.thinBlock().getId()));
            expectedRecipeIds.add(plankRecipeId(set.definition().plankBlock().get(), set.strippedThinBlock().getId()));
        });

        if (!generatedRecipeIds.equals(expectedRecipeIds)) {
            Set<ResourceLocation> missing = new LinkedHashSet<>(expectedRecipeIds);
            missing.removeAll(generatedRecipeIds);

            Set<ResourceLocation> unexpected = new LinkedHashSet<>(generatedRecipeIds);
            unexpected.removeAll(expectedRecipeIds);

            throw new IllegalStateException("Recipe coverage mismatch. Missing=" + missing + ", unexpected=" + unexpected);
        }
    }
}
