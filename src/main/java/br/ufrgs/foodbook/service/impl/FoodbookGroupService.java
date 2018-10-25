package br.ufrgs.foodbook.service.impl;

import br.ufrgs.foodbook.dao.GroupDao;
import br.ufrgs.foodbook.dto.group.GroupRegistrationData;
import br.ufrgs.foodbook.exception.InvalidRegistrationException;
import br.ufrgs.foodbook.exception.ResourceNotFoundException;
import br.ufrgs.foodbook.model.groups.Group;
import br.ufrgs.foodbook.model.security.User;
import br.ufrgs.foodbook.service.GroupService;
import br.ufrgs.foodbook.strategies.converter.AbstractGenericConverter;
import br.ufrgs.foodbook.validator.impl.FoodbookGroupValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.util.function.Consumer;

import static java.util.Objects.isNull;

@Service
public class FoodbookGroupService implements GroupService
{
    private static final String GROUP_NAME_FIELD = "name";
    private static final String GROUP_NAME_ERROR_MESSAGE = "Nome de grupo ja existente!";
    private static final String GENERAL_ERROR_FIELD_NAME = "GENERAL_ERROR";
    private static final String GENERAL_ERROR_MESSAGE = "Você não tem permissão para modificar este grupo ou ele é inválido!";
    private static final String MEMBER_NOT_FOUND_MESSAGE = "Não foi possível localizar o membro ou o grupo designado";
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

        resultPage.getContent().forEach(removeSensitiveData());

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
        Group originalGroup = groupDao.findByName(groupRegistrationData.getName());

        if(isNull(originalGroup) || notRecipeOwnerRequest(groupRegistrationData, originalGroup))
        {
            throw new InvalidRegistrationException(GENERAL_ERROR_FIELD_NAME, GENERAL_ERROR_MESSAGE);
        }

        groupValidator.validate(groupRegistrationData);
        Group group = groupRegistrationReverseConverter.convert(groupRegistrationData);

        group.setId(originalGroup.getId());

        groupDao.save(group);
    }

    @Override
    public void remove(GroupRegistrationData groupRegistrationData)
    {
        Group originalGroup = groupDao.findByName(groupRegistrationData.getName());

        if(isNull(originalGroup) || notRecipeOwnerRequest(groupRegistrationData, originalGroup))
        {
            throw new InvalidRegistrationException(GENERAL_ERROR_FIELD_NAME, GENERAL_ERROR_MESSAGE);
        }

        groupDao.delete(originalGroup);
    }

    private Consumer<Group> removeSensitiveData()
    {
        return group -> {
            User administrator = new User();

            administrator.setUsername(group.getAdministrator().getUsername());
            administrator.setId(group.getAdministrator().getId());

            group.setAdministrator(administrator);

            group.getMembers().stream().map(member -> {
                User user = new User();

                user.setUsername(member.getUsername());
                user.setId(member.getId());

                return user;
            });

            group.getRecipes().forEach(recipe -> {
                User creator = new User();

                creator.setUsername(recipe.getCreator().getUsername());
                creator.setId(recipe.getCreator().getId());

                recipe.setCreator(creator);
            });
        };
    }

    private boolean notRecipeOwnerRequest(GroupRegistrationData recipeRegistration, Group originalRecipe)
    {
        return !recipeRegistration.getCreatorName().equals(originalRecipe.getAdministrator().getUsername());
    }
}
