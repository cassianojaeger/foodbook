package br.ufrgs.foodbook.service.impl;

import br.ufrgs.foodbook.dao.RecipeDao;
import br.ufrgs.foodbook.dto.recipe.RecipeRegistrationData;
import br.ufrgs.foodbook.exception.InvalidRecipeRegistrationException;
import br.ufrgs.foodbook.exception.ResourceNotFoundException;
import br.ufrgs.foodbook.model.recipe.Recipe;
import br.ufrgs.foodbook.model.security.User;
import br.ufrgs.foodbook.service.RecipeService;
import br.ufrgs.foodbook.strategies.converter.AbstractGenericConverter;
import br.ufrgs.foodbook.validator.impl.FoodbookRecipeValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.util.function.Consumer;

import static java.util.Objects.isNull;

@Service
public class FoodbookRecipeService implements RecipeService
{
    private static final String RECIPE_NAME_FIELD = "name";
    private static final String RECIPE_NAME_ERROR_MESSAGE = "Nome de receita ja existente!";
    private static final String GENERAL_ERROR_FIELD_NAME = "GENERAL_ERROR";
    private static final String GENERAL_ERROR_MESSAGE = "Você não tem permissão para modificar esta receita ou ela é inválida!";

    @Resource
    RecipeDao recipeDao;
    @Resource
    private AbstractGenericConverter<RecipeRegistrationData, Recipe> recipeRegistrationReverseConverter;
    @Resource
    private FoodbookRecipeValidator recipeValidator;

    private static final String RESOURCE_SEARCH_ERROR_MESSAGE = "Erro ao buscar as receitas, tente novamente!";

    @Override
    public Page<Recipe> getPaginatedRecipes(int page, int size)
    {
        Page<Recipe> resultPage = recipeDao.findAll(PageRequest.of(page, size));

        if(page > resultPage.getTotalPages())
            throw new ResourceNotFoundException(RESOURCE_SEARCH_ERROR_MESSAGE);

        resultPage.getContent().forEach(removeUserSensitiveData());

        return resultPage;
    }

    @Override
    public void createRecipe(RecipeRegistrationData recipeRegistration)
    {
        recipeValidator.validate(recipeRegistration);
        Recipe recipe = recipeRegistrationReverseConverter.convert(recipeRegistration);

        try {
            recipeDao.save(recipe);
        }
        catch (ConstraintViolationException e)
        {
            throw new InvalidRecipeRegistrationException(RECIPE_NAME_FIELD, RECIPE_NAME_ERROR_MESSAGE);
        }
    }

    @Override
    public void updateRecipe(RecipeRegistrationData recipeRegistration)
    {
        Recipe originalRecipe = recipeDao.findByName(recipeRegistration.getName());

        if(isNull(originalRecipe) || notRecipeOwnerRequest(recipeRegistration, originalRecipe))
        {
            throw new InvalidRecipeRegistrationException(GENERAL_ERROR_FIELD_NAME, GENERAL_ERROR_MESSAGE);
        }

        recipeValidator.validate(recipeRegistration);
        Recipe recipe = recipeRegistrationReverseConverter.convert(recipeRegistration);

        recipe.setId(originalRecipe.getId());

        recipeDao.save(recipe);
    }

    @Override
    public void removeRecipe(RecipeRegistrationData recipeRegistration)
    {
        Recipe originalRecipe = recipeDao.findByName(recipeRegistration.getName());

        if(isNull(originalRecipe) || notRecipeOwnerRequest(recipeRegistration, originalRecipe))
        {
            throw new InvalidRecipeRegistrationException(GENERAL_ERROR_FIELD_NAME, GENERAL_ERROR_MESSAGE);
        }

        recipeDao.delete(originalRecipe);
    }

    private boolean notRecipeOwnerRequest(RecipeRegistrationData recipeRegistration, Recipe originalRecipe)
    {
        return !recipeRegistration.getCreatorName().equals(originalRecipe.getCreator().getUsername());
    }

    private Consumer<Recipe> removeUserSensitiveData()
    {
        return recipe -> {
            User creator = new User();
            User administrator = new User();

            creator.setId(recipe.getCreator().getId());
            creator.setUsername(recipe.getCreator().getUsername());

            administrator.setId(recipe.getGroup().getAdministrator().getId());
            administrator.setUsername(recipe.getGroup().getAdministrator().getUsername());

            recipe.setCreator(creator);
            recipe.getGroup().setAdministrator(administrator);
        };
    }
}