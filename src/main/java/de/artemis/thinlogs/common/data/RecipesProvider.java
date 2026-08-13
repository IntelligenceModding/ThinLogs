package de.artemis.thinlogs.common.data;

import de.artemis.thinlogs.common.registration.ModBlocks;
import de.artemis.thinlogs.common.registration.ThinLogSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RecipesProvider extends RecipeProvider {
    public RecipesProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        Set<Identifier> generatedRecipeIds = new LinkedHashSet<>();
        ModBlocks.allSets().forEach(set -> thinLogRecipes(set, generatedRecipeIds));
        validateRecipeCoverage(generatedRecipeIds);
    }

    private void thinLogRecipes(ThinLogSet set, Set<Identifier> generatedRecipeIds) {
        ItemLike baseBlock = set.definition().baseBlock().get();
        ItemLike strippedBaseBlock = set.definition().strippedBaseBlock().get();
        ItemLike plankBlock = set.definition().plankBlock().get();
        Identifier thinRecipeId = set.thinBlock().getId();
        Identifier strippedThinRecipeId = set.strippedThinBlock().getId();
        Identifier thinToPlanksRecipeId = plankRecipeId(plankBlock, set.thinBlock().getId());
        Identifier strippedThinToPlanksRecipeId = plankRecipeId(plankBlock, set.strippedThinBlock().getId());

        shaped(RecipeCategory.BUILDING_BLOCKS, set.thinBlock().get(), 8)
                .define('A', baseBlock)
                .pattern("A")
                .pattern("A")
                .unlockedBy("has_" + thinRecipeId.getPath(), has(baseBlock))
                .save(output, registerRecipeId(generatedRecipeIds, thinRecipeId));

        shaped(RecipeCategory.BUILDING_BLOCKS, set.strippedThinBlock().get(), 8)
                .define('A', strippedBaseBlock)
                .pattern("A")
                .pattern("A")
                .unlockedBy("has_" + strippedThinRecipeId.getPath(), has(strippedBaseBlock))
                .save(output, registerRecipeId(generatedRecipeIds, strippedThinRecipeId));

        shapeless(RecipeCategory.BUILDING_BLOCKS, plankBlock)
                .requires(set.thinBlock().get())
                .unlockedBy("has_" + set.thinBlock().getId().getPath(), has(set.thinBlock().get()))
                .save(output, registerRecipeId(generatedRecipeIds, thinToPlanksRecipeId));

        shapeless(RecipeCategory.BUILDING_BLOCKS, plankBlock)
                .requires(set.strippedThinBlock().get())
                .unlockedBy("has_" + set.strippedThinBlock().getId().getPath(), has(set.strippedThinBlock().get()))
                .save(output, registerRecipeId(generatedRecipeIds, strippedThinToPlanksRecipeId));
    }

    private static Identifier plankRecipeId(ItemLike plankBlock, Identifier sourceId) {
        String plankName = BuiltInRegistries.ITEM.getKey(plankBlock.asItem()).getPath().replace("_planks", "");
        return Identifier.withDefaultNamespace(plankName + "_planks_from_" + sourceId.getPath());
    }

    private static ResourceKey<Recipe<?>> registerRecipeId(Set<Identifier> generatedRecipeIds, Identifier recipeId) {
        if (!generatedRecipeIds.add(recipeId)) {
            throw new IllegalStateException("Duplicate recipe id generated: " + recipeId);
        }
        return ResourceKey.create(Registries.RECIPE, recipeId);
    }

    private static void validateRecipeCoverage(Set<Identifier> generatedRecipeIds) {
        Set<Identifier> expectedRecipeIds = new LinkedHashSet<>();
        ModBlocks.allSets().forEach(set -> {
            expectedRecipeIds.add(set.thinBlock().getId());
            expectedRecipeIds.add(set.strippedThinBlock().getId());
            expectedRecipeIds.add(plankRecipeId(set.definition().plankBlock().get(), set.thinBlock().getId()));
            expectedRecipeIds.add(plankRecipeId(set.definition().plankBlock().get(), set.strippedThinBlock().getId()));
        });

        if (!generatedRecipeIds.equals(expectedRecipeIds)) {
            Set<Identifier> missing = new LinkedHashSet<>(expectedRecipeIds);
            missing.removeAll(generatedRecipeIds);

            Set<Identifier> unexpected = new LinkedHashSet<>(generatedRecipeIds);
            unexpected.removeAll(expectedRecipeIds);

            throw new IllegalStateException("Recipe coverage mismatch. Missing=" + missing + ", unexpected=" + unexpected);
        }
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new RecipesProvider(registries, output);
        }

        @Override
        public String getName() {
            return "Thin Logs Recipes";
        }
    }
}
