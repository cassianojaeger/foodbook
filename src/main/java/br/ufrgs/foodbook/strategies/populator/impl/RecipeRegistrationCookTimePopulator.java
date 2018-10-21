package br.ufrgs.foodbook.strategies.populator.impl;

import br.ufrgs.foodbook.strategies.populator.Populator;
import br.ufrgs.foodbook.dto.recipe.CookTimeData;
import br.ufrgs.foodbook.dto.recipe.RecipeRegistrationData;
import br.ufrgs.foodbook.model.enums.TimeType;
import br.ufrgs.foodbook.model.recipe.Recipe;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RecipeRegistrationCookTimePopulator implements Populator<RecipeRegistrationData, Recipe>
{
    @Override
    public void populate(RecipeRegistrationData source, Recipe target)
    {
        CookTimeData cookTime = source.getCookTime();
        target.setCookTime(
                Collections.singletonMap(
                        TimeType.valueOf(cookTime.getTimeType()),
                        Integer.valueOf(cookTime.getTimeValue())
                )
        );
    }
}
