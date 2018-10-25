package br.ufrgs.foodbook.strategies.populator.impl;

import br.ufrgs.foodbook.dto.group.GroupInformationData;
import br.ufrgs.foodbook.dto.recipe.RecipeInformationData;
import br.ufrgs.foodbook.model.groups.Group;
import br.ufrgs.foodbook.model.recipe.Recipe;
import br.ufrgs.foodbook.strategies.converter.AbstractGenericConverter;
import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class GroupRecipeInformationPopulator implements Populator<Group, GroupInformationData>
{
    @Resource
    private AbstractGenericConverter<Recipe, RecipeInformationData> recipeInformationConverter;

    @Override
    public void populate(Group source, GroupInformationData target)
    {
        Set<RecipeInformationData> recipes = recipeInformationConverter.convertAll(source.getRecipes());

        target.setRecipes(recipes);
    }
}
