package br.ufrgs.foodbook.strategies.populator.impl;

import br.ufrgs.foodbook.dto.recipe.RecipeInformationData;
import br.ufrgs.foodbook.model.recipe.Recipe;
import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.stereotype.Service;

@Service
public class RecipeInformationGroupPopulator implements Populator<Recipe, RecipeInformationData>
{
    @Override
    public void populate(Recipe source, RecipeInformationData target)
    {
        target.setGroupId(source.getGroup().getId());
    }
}
