package br.ufrgs.foodbook.service;

import br.ufrgs.foodbook.dto.recipe.RecipeFeedbackInformationData;
import br.ufrgs.foodbook.dto.recipe.RecipeFeedbackRegistrationData;
import br.ufrgs.foodbook.dto.recipe.RecipeInformationData;
import org.springframework.data.domain.Page;

public interface ManageRecipeService
{
    void registerFeedback(RecipeFeedbackRegistrationData recipeFeedbackRegistrationData);

    RecipeFeedbackInformationData getRecipeFeedbackAverageValues(Long recipeId);

    Page<RecipeInformationData> getFavoriteRecipes(int page, int size, String username);

    void addRecipeToFavorite(Long recipeId, String username);

    void removeRecipeFromFavorite(Long valueOf, String name);

    void updateFeedback(RecipeFeedbackRegistrationData recipeFeedbackRegistrationData);

    Boolean verifyIfRecipeIsFavorite(Long recipeId, String name);
}
