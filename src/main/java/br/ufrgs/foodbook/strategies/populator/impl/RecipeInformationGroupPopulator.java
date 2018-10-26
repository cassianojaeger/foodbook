package br.ufrgs.foodbook.strategies.populator.impl;

import br.ufrgs.foodbook.dto.group.GroupInformationData;
import br.ufrgs.foodbook.dto.recipe.RecipeInformationData;
import br.ufrgs.foodbook.model.groups.Group;
import br.ufrgs.foodbook.model.recipe.Recipe;
import br.ufrgs.foodbook.strategies.converter.AbstractGenericConverter;
import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RecipeInformationGroupPopulator implements Populator<Recipe, RecipeInformationData>
{
    @Resource
    private AbstractGenericConverter<Group, GroupInformationData> groupInformationConverter;

    @Override
    public void populate(Recipe source, RecipeInformationData target)
    {
        target.setGroup(groupInformationConverter.convert(source.getGroup()));
    }
}
