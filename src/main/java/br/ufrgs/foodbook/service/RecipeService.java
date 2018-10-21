package br.ufrgs.foodbook.service;

import br.ufrgs.foodbook.dto.recipe.RecipeRegistrationData;
import br.ufrgs.foodbook.model.recipe.Recipe;
import org.springframework.data.domain.Page;

public interface RecipeService
{
    Page<Recipe> getPaginatedRecipes(int page, int size);

    void createRecipe(RecipeRegistrationData recipeRegistration);

    void updateRecipe(RecipeRegistrationData recipeInformationData);

    void removeRecipe(RecipeRegistrationData recipeInformationData);
}
