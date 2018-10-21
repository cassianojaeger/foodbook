package br.ufrgs.foodbook.validator.impl;

import br.ufrgs.foodbook.dto.recipe.RecipeRegistrationData;
import br.ufrgs.foodbook.dto.user.UserRegistrationData;
import br.ufrgs.foodbook.exception.InvalidRecipeRegistrationException;
import br.ufrgs.foodbook.validator.Validator;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class FoodbookRecipeValidator implements Validator<RecipeRegistrationData>
{
    private static final String GROUP_NAME_FIELD = "groupName";
    private static final String CREATOR_NAME_FIELD = "creatorName";
    private static final String RECIPE_NAME_FIELD = "name";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String COOK_DIFFICULTY_FIELD = "cookDifficulty";
    private static final String COOK_TIME_FIELD = "cookDifficulty";
    private static final String INGREDIENTS_FIELD = "cookDifficulty";
    private static final String PREPARE_STEPS_FIELD = "prepareSteps";

    private static final String NULL_FIELD_ERROR_MESSAGE = "Por favor, preencha este campo";

    @Override
    public void validate(RecipeRegistrationData recipe)
    {
        validateNotNullFields(recipe);
    }

    private void validateNotNullFields(RecipeRegistrationData recipe)
    {
        if(isNull(recipe.getGroupName()))
            throw new InvalidRecipeRegistrationException(GROUP_NAME_FIELD, NULL_FIELD_ERROR_MESSAGE);

        if(isNull(recipe.getCreatorName()))
            throw new InvalidRecipeRegistrationException(CREATOR_NAME_FIELD, NULL_FIELD_ERROR_MESSAGE);

        if(isNull(recipe.getName()))
            throw new InvalidRecipeRegistrationException(RECIPE_NAME_FIELD, NULL_FIELD_ERROR_MESSAGE);

        if(isNull(recipe.getDescription()))
            throw new InvalidRecipeRegistrationException(DESCRIPTION_FIELD, NULL_FIELD_ERROR_MESSAGE);

        if(isNull(recipe.getCookTime()))
            throw new InvalidRecipeRegistrationException(COOK_TIME_FIELD, NULL_FIELD_ERROR_MESSAGE);

        if(isNull(recipe.getCookDifficulty()))
            throw new InvalidRecipeRegistrationException(COOK_DIFFICULTY_FIELD, NULL_FIELD_ERROR_MESSAGE);

        if(isNull(recipe.getIngredients()))
            throw new InvalidRecipeRegistrationException(INGREDIENTS_FIELD, NULL_FIELD_ERROR_MESSAGE);

        if(isNull(recipe.getPrepareSteps()))
            throw new InvalidRecipeRegistrationException(PREPARE_STEPS_FIELD, NULL_FIELD_ERROR_MESSAGE);
    }
}
