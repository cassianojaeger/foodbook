package br.ufrgs.foodbook.service;

import br.ufrgs.foodbook.dto.group.MemberRequestData;

public interface ManageGroupService
{
    void addMember(MemberRequestData newMemberRequest);

    void removeMember(MemberRequestData newMemberRequest);
}
