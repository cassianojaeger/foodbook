package br.ufrgs.foodbook.strategies.populator.impl;

import br.ufrgs.foodbook.dto.recipe.CookTimeData;
import br.ufrgs.foodbook.dto.recipe.RecipeFeedbackInformationData;
import br.ufrgs.foodbook.model.enums.TimeType;
import br.ufrgs.foodbook.model.recipe.RecipeFeedback;
import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RecipeFeedbackInformationPopulator implements Populator<Set<RecipeFeedback>, RecipeFeedbackInformationData>
{
    private static final Float MINUTES_IN_HOURS = 60f;
    private static final Float SECONDS_IN_MINUTES = 60f;

    private NumberFormat formatter = new DecimalFormat("#0.00");

    @Override
    public void populate(Set<RecipeFeedback> source, RecipeFeedbackInformationData target)
    {
        double cookDifficulty = getMeanValues(source, RecipeFeedback::getCookDifficulty);
        double cookTastyness = getMeanValues(source, RecipeFeedback::getCookTastyness);

        target.setCookDifficulty(Double.valueOf(formatter.format(cookDifficulty)));
        target.setCookTastyness(Double.valueOf(formatter.format(cookTastyness)));

        target.setUsernames(
                 source
                .stream()
                .map(getUsersWhoGaveFeedback())
                .collect(Collectors.toList())
        );

        target.setCookTime(resolveCookTimeAverageValue(source));
    }

    private double getMeanValues(Set<RecipeFeedback> recipeFeedback, Function<RecipeFeedback, Integer> func)
    {
        return recipeFeedback
                .stream()
                .mapToInt(func::apply)
                .average()
                .orElse(Double.NaN);
    }

    private CookTimeData resolveCookTimeAverageValue(Set<RecipeFeedback> recipeFeedback)
    {
        double timeInMinutes = recipeFeedback
                .stream()
                .mapToDouble(this::mapTimeToMinutesRepresentation)
                .average()
                .orElse(Double.NaN);

        CookTimeData cookTime = new CookTimeData();

        cookTime.setTimeValue(formatter.format(timeInMinutes));
        cookTime.setTimeType(TimeType.MINUTES.toString());

        return cookTime;
    }

    private double mapTimeToMinutesRepresentation(RecipeFeedback recipe)
    {
        Map<TimeType, Double> cookTime = recipe.getCookTime();
        TimeType timeType = (TimeType) cookTime.keySet().toArray()[0];
        Double timeValue = (Double) cookTime.values().toArray()[0];

        if(timeType.equals(TimeType.HOURS))
            timeValue = timeValue*MINUTES_IN_HOURS;
        if(timeType.equals(TimeType.SECONDS))
            timeValue = timeValue/SECONDS_IN_MINUTES;

        return timeValue;
    }

    private Function<RecipeFeedback, String> getUsersWhoGaveFeedback()
    {
        return recipeFeedback -> recipeFeedback.getUser().getUsername();
    }
}
