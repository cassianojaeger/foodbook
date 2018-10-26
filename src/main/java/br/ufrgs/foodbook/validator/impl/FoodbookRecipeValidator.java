package br.ufrgs.foodbook.validator.impl;

import br.ufrgs.foodbook.dto.recipe.RecipeRegistrationData;
import br.ufrgs.foodbook.exception.InvalidRegistrationException;
import br.ufrgs.foodbook.validator.Validator;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class FoodbookRecipeValidator implements Validator<RecipeRegistrationData>
{
    private static final String GROUP_ID_FIELD = "groupId";
    private static final String CREATOR_NAME_FIELD = "creatorName";
    private static final String RECIPE_NAME_FIELD = "name";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String COOK_DIFFICULTY_FIELD = "cookDifficulty";
    private static final String COOK_TIME_FIELD = "cookDifficulty";
    private static final String INGREDIENTS_FIELD = "cookDifficulty";
    private static final String PREPARE_STEPS_FIELD = "prepareSteps";

    @Override
    public void validate(RecipeRegistrationData recipe)
    {
        validateNotNullFields(recipe);
    }

    private void validateNotNullFields(RecipeRegistrationData recipe)
    {
        if(isNull(recipe.getGroupId()))
            throw new InvalidRegistrationException(GROUP_ID_FIELD, NULL_FIELD_ERROR_MESSAGE);

        if(isNull(recipe.getCreatorName()))
            throw new InvalidRegistrationException(CREATOR_NAME_FIELD, NULL_FIELD_ERROR_MESSAGE);

        if(isNull(recipe.getName()))
            throw new InvalidRegistrationException(RECIPE_NAME_FIELD, NULL_FIELD_ERROR_MESSAGE);

        if(isNull(recipe.getDescription()))
            throw new InvalidRegistrationException(DESCRIPTION_FIELD, NULL_FIELD_ERROR_MESSAGE);

        if(isNull(recipe.getCookTime()))
            throw new InvalidRegistrationException(COOK_TIME_FIELD, NULL_FIELD_ERROR_MESSAGE);

        if(isNull(recipe.getCookDifficulty()))
            throw new InvalidRegistrationException(COOK_DIFFICULTY_FIELD, NULL_FIELD_ERROR_MESSAGE);

        if(isNull(recipe.getIngredients()))
            throw new InvalidRegistrationException(INGREDIENTS_FIELD, NULL_FIELD_ERROR_MESSAGE);

        if(isNull(recipe.getPrepareSteps()))
            throw new InvalidRegistrationException(PREPARE_STEPS_FIELD, NULL_FIELD_ERROR_MESSAGE);
    }
}
