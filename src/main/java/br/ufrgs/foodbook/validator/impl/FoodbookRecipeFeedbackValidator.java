package br.ufrgs.foodbook.validator.impl;

import br.ufrgs.foodbook.dao.RecipeDao;
import br.ufrgs.foodbook.dao.UserDao;
import br.ufrgs.foodbook.dto.recipe.RecipeFeedbackRegistrationData;
import br.ufrgs.foodbook.exception.InvalidRegistrationException;
import br.ufrgs.foodbook.model.recipe.Recipe;
import br.ufrgs.foodbook.model.security.User;
import br.ufrgs.foodbook.validator.Validator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class FoodbookRecipeFeedbackValidator implements Validator<RecipeFeedbackRegistrationData>
{
    private static final String USER_ID_FIELD = "userId";
    private static final String RECIPE_ID_FIELD = "recipeId";
    private static final String COOK_DIFFICULTY_FIELD = "cookDifficulty";
    private static final String COOK_TASTYNESS_FIELD = "cookTastyness";
    private static final String COOK_TIME_FIELD = "cookTime";
    private static final String USER_NOT_VALID_ERROR_MESSAGE = "Este usuário não existe!";
    private static final String GENERAL_ERROR_FIELD_NAME = "GENERAL_ERROR";
    private static final String GENERAL_ERROR_MESSAGE = "Você não tem permissão para dar este feedback!";

    @Resource
    UserDao userDao;
    @Resource
    RecipeDao recipeDao;

    @Override
    public void validate(RecipeFeedbackRegistrationData feedback)
    {
        validateNullFields(feedback);
        validateRequestCreator(feedback);
        validateRequestRecipe(feedback);
    }

    private void validateRequestRecipe(RecipeFeedbackRegistrationData feedback)
    {
        Optional<Recipe> recipe = recipeDao.findById(feedback.getRecipeId());

        if(!recipe.isPresent())
            throw new InvalidRegistrationException(RECIPE_ID_FIELD, USER_NOT_VALID_ERROR_MESSAGE);
    }

    private void validateRequestCreator(RecipeFeedbackRegistrationData feedback)
    {
        User user = userDao.findByUsername(feedback.getUsername());

        if(isNull(user))
            throw new InvalidRegistrationException(USER_ID_FIELD, USER_NOT_VALID_ERROR_MESSAGE);

        if(!user.getUsername().equals(feedback.getCreatorName()))
            throw new InvalidRegistrationException(GENERAL_ERROR_FIELD_NAME, GENERAL_ERROR_MESSAGE);
    }

    private void validateNullFields(RecipeFeedbackRegistrationData feedback)
    {
        if(isNull(feedback.getUsername()))
            throw new InvalidRegistrationException(USER_ID_FIELD, NULL_FIELD_ERROR_MESSAGE);

        if(isNull(feedback.getRecipeId()))
            throw new InvalidRegistrationException(RECIPE_ID_FIELD, NULL_FIELD_ERROR_MESSAGE);

        if(isNull(feedback.getCookDifficulty()))
            throw new InvalidRegistrationException(COOK_DIFFICULTY_FIELD, NULL_FIELD_ERROR_MESSAGE);

        if(isNull(feedback.getCookTastyness()))
            throw new InvalidRegistrationException(COOK_TASTYNESS_FIELD, NULL_FIELD_ERROR_MESSAGE);

        if(isNull(feedback.getCookTime()))
            throw new InvalidRegistrationException(COOK_TIME_FIELD, NULL_FIELD_ERROR_MESSAGE);
    }
}
