package br.ufrgs.foodbook.service.impl;

import br.ufrgs.foodbook.dao.GroupDao;
import br.ufrgs.foodbook.dto.group.GroupInformationData;
import br.ufrgs.foodbook.dto.group.GroupRegistrationData;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.isNull;

@Service
public class FoodbookGroupService implements GroupService
{
    private static final String GROUP_NAME_FIELD = "name";
    private static final String GROUP_NAME_ERROR_MESSAGE = "Nome de grupo ja existente!";
    private static final String GENERAL_ERROR_FIELD_NAME = "GENERAL_ERROR";
    private static final String GENERAL_ERROR_MESSAGE = "Você não tem permissão para modificar este grupo ou ele é inválido!";
    private static final String RESOURCE_SEARCH_ERROR_MESSAGE = "Erro ao buscar o(s) grupo(s), tente novamente!";

    @Resource
    GroupDao groupDao;
    @Resource
    private AbstractGenericConverter<GroupRegistrationData, Group> groupRegistrationReverseConverter;
    @Resource
    private AbstractGenericConverter<Group, GroupInformationData> groupInformationConverter;
    @Resource
    FoodbookGroupValidator groupValidator;

    @Override
    public Page<GroupInformationData> getPaginatedData(int page, int size)
    {
        return groupDao.findAll(PageRequest.of(page, size)).map(groupInformationConverter::convert);
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
    public GroupInformationData getGroup(Long groupId)
    {
        Optional<Group> group = groupDao.findById(groupId);

        if(!group.isPresent())
            throw new ResourceNotFoundException(RESOURCE_SEARCH_ERROR_MESSAGE);

        return groupInformationConverter.convert(group.get());
    }

    @Override
    public Set<GroupInformationData> searchGroupsByName(String groupName)
    {
        List<Group> groups = groupDao.findByNameIgnoreCaseContaining(groupName);

        return groupInformationConverter.convertAll(new HashSet<>(groups));
    }

    @Override
    public void update(GroupRegistrationData groupRegistrationData)
    {
        Group originalGroup = groupDao.getOne(groupRegistrationData.getId());

        if(isNull(originalGroup) || notRecipeOwnerRequest(groupRegistrationData, originalGroup))
        {
            throw new InvalidRegistrationException(GENERAL_ERROR_FIELD_NAME, GENERAL_ERROR_MESSAGE);
        }

        groupValidator.validate(groupRegistrationData);

        originalGroup.setDescription(groupRegistrationData.getDescription());
        originalGroup.setName(groupRegistrationData.getName());

        groupDao.save(originalGroup);
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

    private boolean notRecipeOwnerRequest(GroupRegistrationData recipeRegistration, Group originalRecipe)
    {
        return !recipeRegistration.getCreatorName().equals(originalRecipe.getAdministrator().getUsername());
    }
}
