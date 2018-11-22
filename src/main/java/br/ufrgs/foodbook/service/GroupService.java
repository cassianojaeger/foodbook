package br.ufrgs.foodbook.service;

import br.ufrgs.foodbook.dto.group.GroupInformationData;
import br.ufrgs.foodbook.dto.group.GroupRegistrationData;

import java.util.Set;

public interface GroupService extends GenericService<GroupRegistrationData, GroupInformationData>
{
    GroupInformationData getGroup(Long groupId);

    Set<GroupInformationData> searchGroupsByName(String groupName);
}
