package br.ufrgs.foodbook.strategies.converter.impl;

import br.ufrgs.foodbook.dto.recipe.RecipeInformationData;
import br.ufrgs.foodbook.model.recipe.Recipe;
import br.ufrgs.foodbook.strategies.converter.AbstractGenericConverter;
import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeInformationConverter extends AbstractGenericConverter<Recipe, RecipeInformationData>
{
    public RecipeInformationConverter()
    {
        super(RecipeInformationData.class);
    }

    @Override
    protected List<Populator<Recipe, RecipeInformationData>> getPopulators()
    {
        return this.populators;
    }

    @Autowired(required = false)
    @Override
    protected void setPopulators(List<Populator<Recipe, RecipeInformationData>> populators)
    {
        this.populators = populators;
    }
}
