package br.ufrgs.foodbook.strategies.populator.impl;

import br.ufrgs.foodbook.dao.GroupDao;
import br.ufrgs.foodbook.dto.recipe.RecipeRegistrationData;
import br.ufrgs.foodbook.exception.InvalidRegistrationException;
import br.ufrgs.foodbook.model.groups.Group;
import br.ufrgs.foodbook.model.recipe.Recipe;
import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static java.util.Objects.isNull;

@Service
public class RecipeRegistrationGroupPopulator implements Populator<RecipeRegistrationData, Recipe>
{
    private static final String GROUP_NOT_FOUND = "Este grupo não existe";
    private static final String GROUP_NAME_FIELD = "groupName";

    @Resource
    GroupDao groupDao;

    @Override
    public void populate(RecipeRegistrationData source, Recipe target)
    {
        String groupName = source.getGroupName();
        Group group = groupDao.findByName(groupName);

        if(isNull(group))
            throw new InvalidRegistrationException(GROUP_NAME_FIELD, GROUP_NOT_FOUND);

        target.setGroup(group);
    }
}
