package br.ufrgs.foodbook.dto.recipe;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeRegistrationData
{
    String name, description, photo, prepareSteps, creatorName, groupName;
    CookTimeData cookTime;
    String ingredients;
    Double cookDifficulty;
}
