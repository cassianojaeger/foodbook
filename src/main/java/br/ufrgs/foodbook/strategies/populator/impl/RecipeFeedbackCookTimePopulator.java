package br.ufrgs.foodbook.strategies.populator.impl;

import br.ufrgs.foodbook.dto.recipe.CookTimeData;
import br.ufrgs.foodbook.dto.recipe.RecipeFeedbackRegistrationData;
import br.ufrgs.foodbook.model.enums.TimeType;
import br.ufrgs.foodbook.model.recipe.RecipeFeedback;
import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RecipeFeedbackCookTimePopulator implements Populator<RecipeFeedbackRegistrationData, RecipeFeedback>
{
    @Override
    public void populate(RecipeFeedbackRegistrationData source, RecipeFeedback target)
    {
        CookTimeData cookTime = source.getCookTime();
        target.setCookTime(
                Collections.singletonMap(
                        TimeType.valueOf(cookTime.getTimeType()),
                        Double.valueOf(cookTime.getTimeValue())
                )
        );
    }
}
