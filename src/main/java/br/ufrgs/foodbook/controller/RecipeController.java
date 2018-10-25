package br.ufrgs.foodbook.controller;

import br.ufrgs.foodbook.dto.recipe.RecipeInformationData;
import br.ufrgs.foodbook.dto.recipe.RecipeRegistrationData;
import br.ufrgs.foodbook.model.recipe.Recipe;
import br.ufrgs.foodbook.service.RecipeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/secured/recipe")
public class RecipeController extends AbstractGenericController
{
    @Resource
    RecipeService recipeService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity createRecipe(@RequestBody RecipeRegistrationData recipeRegistrationData, Principal principal)
    {
        recipeRegistrationData.setCreatorName(principal.getName());
        recipeService.create(recipeRegistrationData);
        return new ResponseEntity(CREATED);
    }

    @GetMapping(params = {"recipeName"})
    @ResponseStatus(value = HttpStatus.OK)
    public RecipeInformationData getRecipeInformation(@RequestParam("recipeName") String recipeName)
    {
        return recipeService.getRecipe(recipeName);
    }

    @GetMapping(params = {"page","size"})
    @ResponseStatus(value = HttpStatus.OK)
    public Page<Recipe> getRecipesPaginated(@RequestParam("page") int page, @RequestParam("size") int size)
    {
        return recipeService.getPaginatedData(page, size);
    }

    @PutMapping(value = "/update")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity updateRecipe(@RequestBody RecipeRegistrationData recipeUpdateRequestData, Principal principal)
    {
        recipeUpdateRequestData.setCreatorName(principal.getName());
        recipeService.update(recipeUpdateRequestData);
        return new ResponseEntity(OK);
    }

    @DeleteMapping(value = "/remove")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity removeRecipe(@RequestBody RecipeRegistrationData recipeRemoveRequestData, Principal principal)
    {
        recipeRemoveRequestData.setCreatorName(principal.getName());
        recipeService.remove(recipeRemoveRequestData);
        return new ResponseEntity(OK);
    }
}
