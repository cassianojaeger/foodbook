package br.ufrgs.foodbook.service;

import br.ufrgs.foodbook.dto.group.GroupRegistrationData;
import br.ufrgs.foodbook.dto.recipe.RecipeRegistrationData;
import br.ufrgs.foodbook.model.groups.Group;

public interface GroupService extends GenericService<GroupRegistrationData, Group>
{
    void addMember(String memberName, String groupName);

    void removeMember(String memberName, String groupName);

    void addRecipe(RecipeRegistrationData recipeRegistrationData, String groupName);

    void removeRecipe(RecipeRegistrationData recipeRegistrationData, String groupName);
}
