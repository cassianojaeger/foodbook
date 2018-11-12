package br.ufrgs.foodbook.dto.recipe;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecipeFeedbackInformationData
{
    List<String> usernames;
    Double cookDifficulty, cookTastyness;
    CookTimeData cookTime;
}
