package br.ufrgs.foodbook.strategies.converter.impl;

import br.ufrgs.foodbook.dto.recipe.RecipeFeedbackInformationData;
import br.ufrgs.foodbook.model.recipe.RecipeFeedback;
import br.ufrgs.foodbook.strategies.converter.AbstractGenericConverter;
import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RecipeFeedbackInformationReverseConverter extends AbstractGenericConverter<Set<RecipeFeedback>, RecipeFeedbackInformationData>
{
    public RecipeFeedbackInformationReverseConverter()
    {
        super(RecipeFeedbackInformationData.class);
    }

    @Override
    protected List<Populator<Set<RecipeFeedback>, RecipeFeedbackInformationData>> getPopulators()
    {
        return this.populators;
    }

    @Autowired(required = false)
    @Override
    protected void setPopulators(List<Populator<Set<RecipeFeedback>, RecipeFeedbackInformationData>> populators)
    {
        this.populators = populators;
    }
}
