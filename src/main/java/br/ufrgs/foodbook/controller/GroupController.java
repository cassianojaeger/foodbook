package br.ufrgs.foodbook.controller;

import br.ufrgs.foodbook.dto.group.GroupRegistrationData;
import br.ufrgs.foodbook.model.groups.Group;
import br.ufrgs.foodbook.service.GroupService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/secured/group")
public class GroupController
{
    @Resource
    GroupService groupService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity createRecipe(@RequestBody GroupRegistrationData groupRegistrationData, Principal principal)
    {
        groupRegistrationData.setCreatorName(principal.getName());
        groupService.create(groupRegistrationData);
        return new ResponseEntity(CREATED);
    }

    @GetMapping(params = {"page","size"})
    @ResponseStatus(value = HttpStatus.OK)
    public Page<Group> getGroupsPaginated(@RequestParam("page") int page, @RequestParam("size") int size)
    {
        return groupService.getPaginatedData(page, size);
    }
}
