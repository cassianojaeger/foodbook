package br.ufrgs.foodbook.strategies.populator.impl;

import br.ufrgs.foodbook.dto.recipe.RecipeInformationData;
import br.ufrgs.foodbook.dto.user.UserInformationData;
import br.ufrgs.foodbook.model.recipe.Recipe;
import br.ufrgs.foodbook.model.security.User;
import br.ufrgs.foodbook.strategies.converter.AbstractGenericConverter;
import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RecipeInformationUserPopulator implements Populator<Recipe, RecipeInformationData>
{
    @Resource
    private AbstractGenericConverter<User, UserInformationData> userInformationConverter;

    @Override
    public void populate(Recipe source, RecipeInformationData target)
    {
        target.setCreator(userInformationConverter.convert(source.getCreator()));
    }
}
