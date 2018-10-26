package br.ufrgs.foodbook.dto.recipe;

import br.ufrgs.foodbook.dto.user.UserInformationData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeInformationData
{
    Long id;
    Long groupId;
    String name, description, photo, prepareSteps;
    UserInformationData creator;
    CookTimeData cookTime;
    String ingredients;
    Integer cookDifficulty;
}
