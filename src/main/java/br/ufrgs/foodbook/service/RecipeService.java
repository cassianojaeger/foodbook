package br.ufrgs.foodbook.service;

import br.ufrgs.foodbook.dto.recipe.RecipeInformationData;
import br.ufrgs.foodbook.dto.recipe.RecipeRegistrationData;
import br.ufrgs.foodbook.model.recipe.Recipe;

import java.util.List;

public interface RecipeService extends GenericService<RecipeRegistrationData, Recipe>
{
    RecipeInformationData getRecipe(String recipeName);
    List<Recipe> getGroupRecipes(String groupName);
}
