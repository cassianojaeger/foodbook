package br.ufrgs.foodbook.strategies.populator.impl;

import br.ufrgs.foodbook.dto.recipe.CookTimeData;
import br.ufrgs.foodbook.dto.recipe.RecipeInformationData;
import br.ufrgs.foodbook.model.recipe.Recipe;
import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RecipeInformationCookTimePopulator implements Populator<Recipe, RecipeInformationData>
{
    @Override
    public void populate(Recipe source, RecipeInformationData target)
    {
        String timeType = source.getCookTime().keySet().toArray()[0].toString();
        String timeValue = source.getCookTime().values().toArray()[0].toString();

        StringUtils.capitalize(timeType);

        CookTimeData cookTimeData = new CookTimeData();

        cookTimeData.setTimeType(timeType);
        cookTimeData.setTimeValue(timeValue);

        target.setCookTime(cookTimeData);
    }
}
