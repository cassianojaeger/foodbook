package br.ufrgs.foodbook.service.impl;

import br.ufrgs.foodbook.dao.GroupDao;
import br.ufrgs.foodbook.dao.UserDao;
import br.ufrgs.foodbook.dto.group.MemberRequestData;
import br.ufrgs.foodbook.exception.InvalidRegistrationException;
import br.ufrgs.foodbook.model.groups.Group;
import br.ufrgs.foodbook.model.security.User;
import br.ufrgs.foodbook.service.ManageGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static java.util.Objects.nonNull;

@Service
public class FoodbookManageGroupService implements ManageGroupService
{
    @Resource
    UserDao userDao;
    @Resource
    GroupDao groupDao;

    private static final String GENERAL_ERROR_FIELD_NAME = "GENERAL_ERROR";
    private static final String MEMBER_NOT_FOUND_MESSAGE = "Não foi possível localizar o membro ou o grupo designado";

    @Override
    public void addMember(MemberRequestData newMemberRequest)
    {
        User newMember = userDao.findByUsername(newMemberRequest.getMemberName());
        Group group = groupDao.getOne(newMemberRequest.getGroupId());

        if(!validMemberRequest(newMemberRequest, newMember, group))
            throw new InvalidRegistrationException(GENERAL_ERROR_FIELD_NAME, MEMBER_NOT_FOUND_MESSAGE);

        group.getMembers().add(newMember);

        groupDao.save(group);
    }

    @Override
    public void removeMember(MemberRequestData newMemberRequest)
    {
        User removedMember = userDao.findByUsername(newMemberRequest.getMemberName());
        Group group = groupDao.getOne(newMemberRequest.getGroupId());

        if(!validMemberRequest(newMemberRequest, removedMember, group))
            throw new InvalidRegistrationException(GENERAL_ERROR_FIELD_NAME, MEMBER_NOT_FOUND_MESSAGE);

        group.getMembers().remove(removedMember);

        groupDao.save(group);
    }

    private boolean validMemberRequest(MemberRequestData newMemberRequest, User member, Group group)
    {
        return nonNull(group) || (nonNull(member) && member.getUsername().equals(newMemberRequest.getCreatorName()));
    }
}
