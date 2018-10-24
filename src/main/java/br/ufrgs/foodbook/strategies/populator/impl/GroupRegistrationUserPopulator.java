package br.ufrgs.foodbook.strategies.populator.impl;

import br.ufrgs.foodbook.dao.UserDao;
import br.ufrgs.foodbook.dto.group.GroupRegistrationData;
import br.ufrgs.foodbook.exception.InvalidRegistrationException;
import br.ufrgs.foodbook.model.groups.Group;
import br.ufrgs.foodbook.model.security.User;
import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static java.util.Objects.isNull;

@Service
public class GroupRegistrationUserPopulator implements Populator<GroupRegistrationData, Group>
{
    private static final String CREATOR_NAME_FIELD = "creatorName";

    @Resource
    UserDao userDao;

    @Override
    public void populate(GroupRegistrationData source, Group target)
    {
        String administrator = source.getCreatorName();
        User user = userDao.findByUsername(administrator);

        if(isNull(user))
            throw new InvalidRegistrationException(CREATOR_NAME_FIELD, USER_NOT_FOUND);

        target.setAdministrator(user);
    }
}
