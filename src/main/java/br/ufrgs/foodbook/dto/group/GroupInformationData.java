package br.ufrgs.foodbook.dto.group;

import br.ufrgs.foodbook.dto.recipe.RecipeInformationData;
import br.ufrgs.foodbook.dto.user.UserInformationData;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class GroupInformationData
{
    String name, description;
    UserInformationData administrator;
    Set<UserInformationData> members;
    Set<RecipeInformationData> recipes;
}
