package br.ufrgs.foodbook.strategies.populator.impl;

import br.ufrgs.foodbook.strategies.populator.Populator;
import br.ufrgs.foodbook.dao.UserDao;
import br.ufrgs.foodbook.dto.recipe.RecipeRegistrationData;
import br.ufrgs.foodbook.exception.InvalidRecipeRegistrationException;
import br.ufrgs.foodbook.model.recipe.Recipe;
import br.ufrgs.foodbook.model.security.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static java.util.Objects.isNull;

@Service
public class RecipeRegistrationUserPopulator implements Populator<RecipeRegistrationData, Recipe>
{
    private static final String CREATOR_NAME_FIELD = "creatorName";
    private static final String USER_NOT_FOUND = "Este usuário não existe";

    @Resource
    UserDao userDao;

    @Override
    public void populate(RecipeRegistrationData source, Recipe target)
    {
        String creatorName = source.getCreatorName();
        User user = userDao.findByUsername(creatorName);

        if(isNull(user))
            throw new InvalidRecipeRegistrationException(CREATOR_NAME_FIELD, USER_NOT_FOUND);

        target.setCreator(user);
    }
}
