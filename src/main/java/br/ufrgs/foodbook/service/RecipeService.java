package br.ufrgs.foodbook.service;

import br.ufrgs.foodbook.dto.recipe.RecipeInformationData;
import br.ufrgs.foodbook.dto.recipe.RecipeRegistrationData;
import br.ufrgs.foodbook.model.recipe.Recipe;

import java.util.List;
import java.util.Set;

public interface RecipeService extends GenericService<RecipeRegistrationData, Recipe>
{
    RecipeInformationData getRecipe(Long recipeId);
    List<Recipe> getGroupRecipes(Long groupId);
    Set<RecipeInformationData> searchRecipesByName(String recipeName);
}
