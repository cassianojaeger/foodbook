package br.ufrgs.foodbook.controller;

import br.ufrgs.foodbook.dto.recipe.RecipeInformationData;
import br.ufrgs.foodbook.dto.user.UserInformationData;
import br.ufrgs.foodbook.dto.user.UserRegistrationData;
import br.ufrgs.foodbook.dto.user.UserUpdateData;
import br.ufrgs.foodbook.service.ManageRecipeService;
import br.ufrgs.foodbook.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/secured/user")
public class UserInformationController extends AbstractGenericController
{
    @Resource
    private UserService userService;
    @Resource
    ManageRecipeService manageRecipeService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Principal get(Principal principal) {
        return principal;
    }

    @GetMapping(value = "/{username}/getFavorites")
    @ResponseStatus(value = HttpStatus.OK)
    public Page<RecipeInformationData> getRecipeFeedbackInformation(
            @PathVariable("username") String username,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size)
    {
        return manageRecipeService.getFavoriteRecipes(page, size, username);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity create(@RequestBody UserRegistrationData user) {
        userService.registerNewUser(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{userName}/updateUser")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity updateUser(UserUpdateData user, Principal principal,
                                     @PathVariable("userName") String userName)
    {
        user.setCreatorName(principal.getName());
        user.setUsername(userName);
        userService.updateUser(user);
        return new ResponseEntity(OK);
    }

    @GetMapping(value = "/{userName}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserInformationData getUserInformation(@PathVariable("userName") String userName)
    {
        return userService.getUserInformation(userName);
    }
}
