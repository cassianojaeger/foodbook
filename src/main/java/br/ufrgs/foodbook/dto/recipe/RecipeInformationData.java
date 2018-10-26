package br.ufrgs.foodbook.dto.recipe;

import br.ufrgs.foodbook.dto.group.GroupInformationData;
import br.ufrgs.foodbook.dto.user.UserInformationData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeInformationData
{
    Long id;
    GroupInformationData group;
    String name, description, photo, prepareSteps;
    UserInformationData creator;
    CookTimeData cookTime;
    String ingredients;
    Double cookDifficulty;
}
