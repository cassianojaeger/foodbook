package br.ufrgs.foodbook.dto.recipe;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeRegistrationData
{
    Long recipeId;
    Long groupId;
    String name, description, photo, prepareSteps, creatorName;
    CookTimeData cookTime;
    String ingredients;
    Double cookDifficulty;
}
