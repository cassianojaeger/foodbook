package br.ufrgs.foodbook.service.impl;

import br.ufrgs.foodbook.dao.RecipeDao;
import br.ufrgs.foodbook.dao.UserDao;
import br.ufrgs.foodbook.dto.recipe.RecipeInformationData;
import br.ufrgs.foodbook.dto.recipe.RecipeRegistrationData;
import br.ufrgs.foodbook.exception.InvalidRegistrationException;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

@Service
public class FoodbookRecipeService implements RecipeService
{
    private static final String GENERAL_ERROR_FIELD_NAME = "GENERAL_ERROR";
    private static final String GENERAL_ERROR_MESSAGE = "Você não tem permissão para modificar esta receita ou ela é inválida!";
    private static final String RESOURCE_SEARCH_ERROR_MESSAGE = "Erro ao buscar as receitas, tente novamente!";

    @Resource
    RecipeDao recipeDao;
    @Resource
    UserDao userDao;
    @Resource
    private AbstractGenericConverter<RecipeRegistrationData, Recipe> recipeRegistrationReverseConverter;
    @Resource
    private AbstractGenericConverter<Recipe, RecipeInformationData> recipeInformationConverter;
    @Resource
    private FoodbookRecipeValidator recipeValidator;

    @Override
    public Page<Recipe> getPaginatedData(int page, int size)
    {
        Page<Recipe> resultPage = recipeDao.findAll(PageRequest.of(page, size));

        if(page > resultPage.getTotalPages())
            throw new ResourceNotFoundException(RESOURCE_SEARCH_ERROR_MESSAGE);

        resultPage.getContent().forEach(removeUserSensitiveData());

        return resultPage;
    }

    @Override
    public RecipeInformationData getRecipe(Long recipeId)
    {
        Optional<Recipe> recipe= recipeDao.findById(recipeId);

        if(!recipe.isPresent())
            throw new ResourceNotFoundException(RESOURCE_SEARCH_ERROR_MESSAGE);

        return recipeInformationConverter.convert(recipe.get());
    }

    @Override
    public List<Recipe> getGroupRecipes(Long groupId) {
        return recipeDao.findAllById(groupId);
    }

    @Override
    public Set<RecipeInformationData> searchRecipesByName(String recipeName)
    {
        List<Recipe> recipes = recipeDao.findByNameIgnoreCaseContaining(recipeName);

        return recipeInformationConverter.convertAll(new HashSet<>(recipes));
    }

    @Override
    public void create(RecipeRegistrationData recipeRegistration)
    {
        recipeValidator.validate(recipeRegistration);
        Recipe recipe = recipeRegistrationReverseConverter.convert(recipeRegistration);

        recipeDao.save(recipe);
    }

    @Override
    public void update(RecipeRegistrationData recipeRegistration)
    {
        Optional<Recipe> optionalOriginalRecipe = recipeDao.findById(recipeRegistration.getRecipeId());

        if(!optionalOriginalRecipe.isPresent() || notRecipeOwnerRequest(recipeRegistration, optionalOriginalRecipe.get()))
        {
            throw new InvalidRegistrationException(GENERAL_ERROR_FIELD_NAME, GENERAL_ERROR_MESSAGE);
        }

        recipeValidator.validate(recipeRegistration);
        Recipe updatedRecipe = recipeRegistrationReverseConverter.convert(recipeRegistration);

        Recipe originalRecipe = optionalOriginalRecipe.get();

        originalRecipe.setCookTime(updatedRecipe.getCookTime());
        originalRecipe.setName(updatedRecipe.getName());
        originalRecipe.setDescription(updatedRecipe.getDescription());
        originalRecipe.setCookDifficulty(updatedRecipe.getCookDifficulty());
        originalRecipe.setIngredients(updatedRecipe.getIngredients());
        originalRecipe.setPhoto(updatedRecipe.getPhoto());
        originalRecipe.setPrepareSteps(updatedRecipe.getPrepareSteps());

        userDao.save(originalRecipe.getCreator());
    }

    @Override
    public void remove(RecipeRegistrationData recipeRegistration)
    {
        Optional<Recipe> optionalOriginalRecipe = recipeDao.findById(recipeRegistration.getRecipeId());

        if(!optionalOriginalRecipe.isPresent() || notRecipeOwnerRequest(recipeRegistration, optionalOriginalRecipe.get()))
        {
            throw new InvalidRegistrationException(GENERAL_ERROR_FIELD_NAME, GENERAL_ERROR_MESSAGE);
        }

        recipeDao.delete(optionalOriginalRecipe.get());
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
