package br.ufrgs.foodbook.service.impl;

import br.ufrgs.foodbook.dao.RecipeDao;
import br.ufrgs.foodbook.dao.RecipeFeedbackDao;
import br.ufrgs.foodbook.dao.UserDao;
import br.ufrgs.foodbook.dto.recipe.RecipeFeedbackInformationData;
import br.ufrgs.foodbook.dto.recipe.RecipeFeedbackRegistrationData;
import br.ufrgs.foodbook.dto.recipe.RecipeInformationData;
import br.ufrgs.foodbook.exception.InvalidRegistrationException;
import br.ufrgs.foodbook.model.recipe.Recipe;
import br.ufrgs.foodbook.model.recipe.RecipeFeedback;
import br.ufrgs.foodbook.model.security.User;
import br.ufrgs.foodbook.service.ManageRecipeService;
import br.ufrgs.foodbook.strategies.converter.AbstractGenericConverter;
import br.ufrgs.foodbook.validator.impl.FoodbookRecipeFeedbackValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.isNull;

@Service
public class FoodbookManageRecipeService implements ManageRecipeService
{
    private static final String GENERAL_ERROR_FIELD_NAME = "GENERAL_ERROR";
    private static final String GIVEN_FEEDBACK_ERROR_MESSAGE = "Você ja deu seu feedback sobre essa receita!";
    private static final String RECIPE_ID_FIELD_NAME = "recipeId";
    private static final String RECIPE_ID_FIELD_ERROR_MESSAGE = "Esta receita não existe!";
    private static final String NULL_ERROR_MESSAGE = "Receita ou usuário nao existentes!";

    @Resource
    FoodbookRecipeFeedbackValidator foodbookRecipeFeedbackValidator;
    @Resource
    AbstractGenericConverter<RecipeFeedbackRegistrationData, RecipeFeedback> recipeFeedbackRegistrationConverter;
    @Resource
    AbstractGenericConverter<Set<RecipeFeedback>, RecipeFeedbackInformationData> recipeFeedbackInformationReverseConverter;
    @Resource
    AbstractGenericConverter<Recipe, RecipeInformationData> recipeInformationConverter;
    @Resource
    RecipeDao recipeDao;
    @Resource
    UserDao userDao;
    @Resource
    RecipeFeedbackDao recipeFeedbackDao;

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
            throw new InvalidRegistrationException(GENERAL_ERROR_FIELD_NAME, GIVEN_FEEDBACK_ERROR_MESSAGE);
        }
    }

    @Override
    public RecipeFeedbackInformationData getRecipeFeedbackAverageValues(Long recipeId)
    {
        Optional<Recipe> recipe = recipeDao.findById(recipeId);

        if(!recipe.isPresent())
            throw new InvalidRegistrationException(RECIPE_ID_FIELD_NAME, RECIPE_ID_FIELD_ERROR_MESSAGE);

        Set<RecipeFeedback> recipeFeedback = recipe.get().getRecipeFeedbacks();

        return recipeFeedbackInformationReverseConverter.convert(recipeFeedback);
    }

    @Override
    public Page<RecipeInformationData> getFavoriteRecipes(int page, int size, String username)
    {
        return recipeDao.findFavoriteRecipes(username, PageRequest.of(page, size)).map(recipeInformationConverter::convert);
    }

    @Override
    public void addRecipeToFavorite(Long recipeId, String username)
    {
        Optional<Recipe> oRecipe = recipeDao.findById(recipeId);
        Optional<User> oUser = Optional.ofNullable(userDao.findByUsername(username));

        if(!oRecipe.isPresent() || !oUser.isPresent())
            throw new InvalidRegistrationException(GENERAL_ERROR_FIELD_NAME, NULL_ERROR_MESSAGE);

        Recipe recipe = oRecipe.get();
        User user = oUser.get();

        user.getFavoriteRecipes().add(recipe);

        userDao.save(user);
    }

    @Override
    public void removeRecipeFromFavorite(Long recipeId, String username)
    {
        Optional<Recipe> oRecipe = recipeDao.findById(recipeId);
        Optional<User> oUser = Optional.ofNullable(userDao.findByUsername(username));

        if(!oRecipe.isPresent() || !oUser.isPresent())
            throw new InvalidRegistrationException(GENERAL_ERROR_FIELD_NAME, NULL_ERROR_MESSAGE);

        Recipe recipe = oRecipe.get();
        User user = oUser.get();

        user.getFavoriteRecipes().remove(recipe);

        userDao.save(user);
    }

    @Override
    public void updateFeedback(RecipeFeedbackRegistrationData recipeFeedbackRegistrationData)
    {
        foodbookRecipeFeedbackValidator.validate(recipeFeedbackRegistrationData);

        Optional<Recipe> oRecipe = recipeDao.findById(recipeFeedbackRegistrationData.getRecipeId());
        User user = userDao.findByUsername(recipeFeedbackRegistrationData.getUsername());

        if(!oRecipe.isPresent() || isNull(user))
            throw new InvalidRegistrationException(GENERAL_ERROR_FIELD_NAME, NULL_ERROR_MESSAGE);

        RecipeFeedback feedback = recipeFeedbackRegistrationConverter.convert(recipeFeedbackRegistrationData);
        RecipeFeedback originalFeedback = recipeFeedbackDao.findByRecipeAndUser(oRecipe.get(), user);

        originalFeedback.setCookDifficulty(feedback.getCookDifficulty());
        originalFeedback.setCookTastyness(feedback.getCookTastyness());
        originalFeedback.setCookTime(feedback.getCookTime());

        try
        {
            userDao.save(originalFeedback.getUser());
        }
        catch (ConstraintViolationException e)
        {
            throw new InvalidRegistrationException(GENERAL_ERROR_FIELD_NAME, GIVEN_FEEDBACK_ERROR_MESSAGE);
        }
    }
}
