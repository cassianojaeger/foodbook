package br.ufrgs.foodbook.service;

import br.ufrgs.foodbook.dto.recipe.RecipeInformationData;
import br.ufrgs.foodbook.dto.recipe.RecipeRegistrationData;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface RecipeService extends GenericService<RecipeRegistrationData, RecipeInformationData>
{
    RecipeInformationData getRecipe(Long recipeId);
    Page<RecipeInformationData> getGroupRecipes(Long groupId, int page, int size);
    Set<RecipeInformationData> searchRecipesByName(String recipeName);
}
