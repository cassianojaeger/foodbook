package br.ufrgs.foodbook.dto.recipe;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeFeedbackRegistrationData
{
    Long groupId, userId;
    Integer cookTastyness, cookDifficulty;
    CookTimeData cookTime;
}
