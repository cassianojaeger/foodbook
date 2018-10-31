package br.ufrgs.foodbook.dto.recipe;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeFeedbackInformationData
{
    Double cookDifficulty, cookTastyness;
    CookTimeData cookTime;
}
