package br.ufrgs.foodbook.controller;

import br.ufrgs.foodbook.dto.recipe.RecipeInformationData;
import br.ufrgs.foodbook.dto.recipe.RecipeRegistrationData;
import br.ufrgs.foodbook.exception.InvalidRecipeRegistrationException;
import br.ufrgs.foodbook.exception.ResourceNotFoundException;
import br.ufrgs.foodbook.model.recipe.Recipe;
import br.ufrgs.foodbook.service.RecipeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/secured/recipe")
public class RecipeController
{
    @Resource
    RecipeService recipeService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity createRecipe(@RequestBody RecipeRegistrationData recipeRegistration, Principal principal)
    {
        recipeRegistration.setCreatorName(principal.getName());
        recipeService.createRecipe(recipeRegistration);
        return new ResponseEntity(CREATED);
    }

    @GetMapping(params = {"page","size"})
    @ResponseStatus(value = HttpStatus.OK)
    public Page<Recipe> getRecipesPaginated(@RequestParam("page") int page, @RequestParam("size") int size)
    {
        return recipeService.getPaginatedRecipes(page, size);
    }

    @PostMapping(value = "/update")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity updateRecipe(@RequestBody RecipeRegistrationData recipeUpdateRequestData, Principal principal)
    {
        recipeUpdateRequestData.setCreatorName(principal.getName());
        recipeService.updateRecipe(recipeUpdateRequestData);
        return new ResponseEntity(OK);
    }

    @PostMapping(value = "/remove")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity removeRecipe(@RequestBody RecipeRegistrationData recipeRemoveRequestData, Principal principal)
    {
        recipeRemoveRequestData.setCreatorName(principal.getName());
        recipeService.removeRecipe(recipeRemoveRequestData);
        return new ResponseEntity(OK);
    }

    @ExceptionHandler({ ResourceNotFoundException.class })
    @ResponseBody
    public ResponseEntity handlePaginationError(final ResourceNotFoundException e)
    {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler({ InvalidRecipeRegistrationException.class })
    @ResponseBody
    public ResponseEntity handlePaginationError(final InvalidRecipeRegistrationException e)
    {
        Map<String, String> errorMessage = new HashMap<>();

        errorMessage.put(e.getFieldName(), e.getErrorMessage());

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
