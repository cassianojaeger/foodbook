package br.ufrgs.foodbook.strategies.converter.impl;

import br.ufrgs.foodbook.dto.recipe.RecipeFeedbackRegistrationData;
import br.ufrgs.foodbook.model.recipe.RecipeFeedback;
import br.ufrgs.foodbook.strategies.converter.AbstractGenericConverter;
import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeFeedbackRegistrationConverter extends AbstractGenericConverter<RecipeFeedbackRegistrationData, RecipeFeedback>
{
    public RecipeFeedbackRegistrationConverter()
    {
        super(RecipeFeedback.class);
    }

    @Override
    protected List<Populator<RecipeFeedbackRegistrationData, RecipeFeedback>> getPopulators()
    {
        return this.populators;
    }

    @Autowired(required = false)
    @Override
    protected void setPopulators(List<Populator<RecipeFeedbackRegistrationData, RecipeFeedback>> populators)
    {
        this.populators = populators;
    }
}
