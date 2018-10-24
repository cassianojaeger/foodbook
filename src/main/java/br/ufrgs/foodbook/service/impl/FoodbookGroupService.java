package br.ufrgs.foodbook.service.impl;

import br.ufrgs.foodbook.dao.GroupDao;
import br.ufrgs.foodbook.dto.group.GroupRegistrationData;
import br.ufrgs.foodbook.dto.recipe.RecipeRegistrationData;
import br.ufrgs.foodbook.exception.InvalidRegistrationException;
import br.ufrgs.foodbook.exception.ResourceNotFoundException;
import br.ufrgs.foodbook.model.groups.Group;
import br.ufrgs.foodbook.service.GroupService;
import br.ufrgs.foodbook.strategies.converter.AbstractGenericConverter;
import br.ufrgs.foodbook.validator.impl.FoodbookGroupValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.util.function.Consumer;

@Service
public class FoodbookGroupService implements GroupService
{
    private static final String GROUP_NAME_FIELD = "name";
    private static final String GROUP_NAME_ERROR_MESSAGE = "Nome de grupo ja existente!";
    private static final String GENERAL_ERROR_FIELD_NAME = "GENERAL_ERROR";
    private static final String GENERAL_ERROR_MESSAGE = "Você não tem permissão para modificar este grupo ou ele é inválido!";
    private static final String RESOURCE_SEARCH_ERROR_MESSAGE = "Erro ao buscar os grupos, tente novamente!";

    @Resource
    GroupDao groupDao;
    @Resource
    private AbstractGenericConverter<GroupRegistrationData, Group> groupRegistrationReverseConverter;
    @Resource
    FoodbookGroupValidator groupValidator;

    @Override
    public Page<Group> getPaginatedData(int page, int size)
    {
        Page<Group> resultPage = groupDao.findAll(PageRequest.of(page, size));

        if(page > resultPage.getTotalPages())
            throw new ResourceNotFoundException(RESOURCE_SEARCH_ERROR_MESSAGE);

        resultPage.getContent().forEach(removeUserSensitiveData());

        return resultPage;
    }

    @Override
    public void create(GroupRegistrationData groupRegistrationData)
    {
        groupValidator.validate(groupRegistrationData);
        Group group = groupRegistrationReverseConverter.convert(groupRegistrationData);

        try {
            groupDao.save(group);
        }
        catch (ConstraintViolationException e)
        {
            throw new InvalidRegistrationException(GROUP_NAME_FIELD, GROUP_NAME_ERROR_MESSAGE);
        }
    }

    @Override
    public void update(GroupRegistrationData groupRegistrationData)
    {

    }

    @Override
    public void remove(GroupRegistrationData groupRegistrationData)
    {

    }

    @Override
    public void addMember(String memberName, String groupName)
    {

    }

    @Override
    public void removeMember(String memberName, String groupName)
    {

    }

    @Override
    public void addRecipe(RecipeRegistrationData recipeRegistrationData, String groupName)
    {

    }

    @Override
    public void removeRecipe(RecipeRegistrationData recipeRegistrationData, String groupName)
    {

    }

    private Consumer<? super Group> removeUserSensitiveData()
    {
        return null;
    }
}
