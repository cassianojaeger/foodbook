package br.ufrgs.foodbook.strategies.converter.impl;

import br.ufrgs.foodbook.dto.recipe.RecipeFeedbackInformationData;
import br.ufrgs.foodbook.model.recipe.RecipeFeedback;
import br.ufrgs.foodbook.strategies.converter.AbstractGenericConverter;
import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeFeedbackInformationReverseConverter extends AbstractGenericConverter<List<RecipeFeedback>, RecipeFeedbackInformationData>
{
    public RecipeFeedbackInformationReverseConverter()
    {
        super(RecipeFeedbackInformationData.class);
    }

    @Override
    protected List<Populator<List<RecipeFeedback>, RecipeFeedbackInformationData>> getPopulators()
    {
        return this.populators;
    }

    @Autowired(required = false)
    @Override
    protected void setPopulators(List<Populator<List<RecipeFeedback>, RecipeFeedbackInformationData>> populators)
    {
        this.populators = populators;
    }
}
