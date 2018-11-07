package br.ufrgs.foodbook.dto.recipe;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeFeedbackRegistrationData
{
    String creatorName, username;
    Long recipeId;
    Integer cookTastyness, cookDifficulty;
    CookTimeData cookTime;
}
