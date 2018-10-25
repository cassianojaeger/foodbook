package br.ufrgs.foodbook.strategies.converter.impl;

import br.ufrgs.foodbook.strategies.converter.AbstractGenericConverter;
import br.ufrgs.foodbook.strategies.populator.Populator;
import br.ufrgs.foodbook.dto.recipe.RecipeRegistrationData;
import br.ufrgs.foodbook.model.recipe.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeRegistrationReverseConverter extends AbstractGenericConverter<RecipeRegistrationData, Recipe>
{
    public RecipeRegistrationReverseConverter()
    {
        super(Recipe.class);
    }

    @Override
    protected List<Populator<RecipeRegistrationData, Recipe>> getPopulators()
    {
        return this.populators;
    }

    @Autowired(required = false)
    @Override
    protected void setPopulators(List<Populator<RecipeRegistrationData, Recipe>> populators)
    {
        this.populators = populators;
    }
}
