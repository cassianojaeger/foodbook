package br.ufrgs.foodbook.strategies.populator.impl;

import br.ufrgs.foodbook.dto.recipe.CookTimeData;
import br.ufrgs.foodbook.model.enums.TimeType;
import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class CookTimePopulator implements Populator<CookTimeData, Map<TimeType, Integer>>

{
    @Override
    public void populate(CookTimeData source, Map<TimeType, Integer> target)
    {
        CookTimeData cookTime = source;
        target = Collections.singletonMap(
                        TimeType.valueOf(cookTime.getTimeType()),
                        Integer.valueOf(cookTime.getTimeValue())
                );
    }
}
