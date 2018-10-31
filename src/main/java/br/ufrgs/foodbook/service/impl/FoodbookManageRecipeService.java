package br.ufrgs.foodbook.service.impl;

import br.ufrgs.foodbook.dao.RecipeDao;
import br.ufrgs.foodbook.dao.RecipeFeedbackDao;
import br.ufrgs.foodbook.dto.recipe.RecipeFeedbackInformationData;
import br.ufrgs.foodbook.dto.recipe.RecipeFeedbackRegistrationData;
import br.ufrgs.foodbook.exception.InvalidRegistrationException;
import br.ufrgs.foodbook.model.recipe.Recipe;
import br.ufrgs.foodbook.model.recipe.RecipeFeedback;
import br.ufrgs.foodbook.service.ManageRecipeService;
import br.ufrgs.foodbook.strategies.converter.AbstractGenericConverter;
import br.ufrgs.foodbook.validator.impl.FoodbookRecipeFeedbackValidator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class FoodbookManageRecipeService implements ManageRecipeService
{
    private static final String GENERAL_ERROR_FIELD_NAME = "GENERAL_ERROR";
    private static final String GENERAL_ERROR_MESSAGE = "Você ja deu seu feedback sobre essa receita!";
    private static final String RECIPE_ID_FIELD_NAME = "recipeId";
    private static final String RECIPE_ID_FIELD_ERROR_MESSAGE = "Esta receita não existe!";


    @Resource
    RecipeFeedbackDao recipeFeedbackDao;
    @Resource
    FoodbookRecipeFeedbackValidator foodbookRecipeFeedbackValidator;
    @Resource
    AbstractGenericConverter<RecipeFeedbackRegistrationData, RecipeFeedback> recipeFeedbackRegistrationConverter;
    @Resource
    AbstractGenericConverter<List<RecipeFeedback>, RecipeFeedbackInformationData> recipeFeedbackInformationReverseConverter;
    @Resource
    RecipeDao recipeDao;

    @Override
    public void registerFeedback(RecipeFeedbackRegistrationData recipeFeedbackRegistrationData)
    {
        foodbookRecipeFeedbackValidator.validate(recipeFeedbackRegistrationData);

        RecipeFeedback feedback = recipeFeedbackRegistrationConverter.convert(recipeFeedbackRegistrationData);

        try
        {
            recipeFeedbackDao.save(feedback);
        }
        catch (ConstraintViolationException e)
        {
            throw new InvalidRegistrationException(GENERAL_ERROR_FIELD_NAME, GENERAL_ERROR_MESSAGE);
        }
    }

    @Override
    public RecipeFeedbackInformationData getRecipeFeedbackAverageValues(Long recipeId)
    {
        Optional<Recipe> recipe = recipeDao.findById(recipeId);

        if(!recipe.isPresent())
            throw new InvalidRegistrationException(RECIPE_ID_FIELD_NAME, RECIPE_ID_FIELD_ERROR_MESSAGE);

        List<RecipeFeedback> recipeFeedback = recipe.get().getRecipeFeedbacks();

        return recipeFeedbackInformationReverseConverter.convert(recipeFeedback);
    }
}
