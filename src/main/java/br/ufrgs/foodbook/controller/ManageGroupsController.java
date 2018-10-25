package br.ufrgs.foodbook.controller;

import br.ufrgs.foodbook.dto.group.MemberRequestData;
import br.ufrgs.foodbook.service.ManageGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/secured/manage/group")
public class ManageGroupsController
{
    @Resource
    ManageGroupService manageGroupService;

    @PostMapping(value = "/addMember")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity addMember(@RequestBody MemberRequestData addNewMemberRequestData, Principal principal)
    {
        addNewMemberRequestData.setCreatorName(principal.getName());
        manageGroupService.addMember(addNewMemberRequestData);
        return new ResponseEntity(CREATED);
    }

    @PostMapping(value = "/removeMember")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity removeMember(@RequestBody MemberRequestData removeMemberRequestData, Principal principal)
    {
        removeMemberRequestData.setCreatorName(principal.getName());
        manageGroupService.removeMember(removeMemberRequestData);
        return new ResponseEntity(CREATED);
    }
}
