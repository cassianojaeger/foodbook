package br.ufrgs.foodbook.strategies.populator.impl;

import br.ufrgs.foodbook.dto.group.GroupInformationData;
import br.ufrgs.foodbook.dto.user.UserInformationData;
import br.ufrgs.foodbook.model.groups.Group;
import br.ufrgs.foodbook.model.security.User;
import br.ufrgs.foodbook.strategies.converter.AbstractGenericConverter;
import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class GroupUserInformationPopulator implements Populator<Group, GroupInformationData>
{
    @Resource
    private AbstractGenericConverter<User, UserInformationData> userInformationConverter;

    @Override
    public void populate(Group source, GroupInformationData target)
    {
        UserInformationData administrator = userInformationConverter.convert(source.getAdministrator());
        Set<UserInformationData> members = userInformationConverter.convertAll(source.getMembers());

        target.setAdministrator(administrator);
        target.setMembers(members);
    }
}
