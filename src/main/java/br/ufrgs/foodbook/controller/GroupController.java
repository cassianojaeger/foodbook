package br.ufrgs.foodbook.controller;

import br.ufrgs.foodbook.model.groups.Group;
import br.ufrgs.foodbook.service.GroupService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/secured/group")
public class GroupController
{
    @Resource
    GroupService groupService;

    @GetMapping(params = {"page","size"})
    @ResponseStatus(value = HttpStatus.OK)
    public Page<Group> getGroupsPaginated(@RequestParam("page") int page, @RequestParam("size") int size)
    {
        return groupService.getPaginatedData(page, size);
    }
}
