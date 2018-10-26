package br.ufrgs.foodbook.service;

import br.ufrgs.foodbook.dto.group.GroupInformationData;
import br.ufrgs.foodbook.dto.group.GroupRegistrationData;
import br.ufrgs.foodbook.model.groups.Group;

import java.util.Set;

public interface GroupService extends GenericService<GroupRegistrationData, Group>
{
    GroupInformationData getGroup(Long groupId);

    Set<GroupInformationData> searchGroupsByName(String groupName);
}
