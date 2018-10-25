package br.ufrgs.foodbook.strategies.populator.impl;

import br.ufrgs.foodbook.dto.recipe.CookTimeData;
import br.ufrgs.foodbook.dto.recipe.RecipeInformationData;
import br.ufrgs.foodbook.model.recipe.Recipe;
import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.stereotype.Service;

@Service
public class RecipeInformationCookTimePopulator implements Populator<Recipe, RecipeInformationData>
{
    @Override
    public void populate(Recipe source, RecipeInformationData target)
    {
        String timeType = (String) source.getCookTime().keySet().toArray()[0];
        String timeValue = (String) source.getCookTime().values().toArray()[0];

        CookTimeData cookTimeData = new CookTimeData();

        cookTimeData.setTimeType(timeType);
        cookTimeData.setTimeValue(timeValue);

        target.setCookTime(cookTimeData);
    }
}
