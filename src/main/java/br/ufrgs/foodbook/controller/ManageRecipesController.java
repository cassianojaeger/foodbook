package br.ufrgs.foodbook.controller;

import br.ufrgs.foodbook.dto.recipe.RecipeFeedbackInformationData;
import br.ufrgs.foodbook.dto.recipe.RecipeFeedbackRegistrationData;
import br.ufrgs.foodbook.service.ManageRecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/secured/manage/recipe")
public class ManageRecipesController extends AbstractGenericController
{
    @Resource
    ManageRecipeService manageRecipeService;

    @PostMapping(value = "/{recipeId}/giveFeedback")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity registerFeedback(@RequestBody RecipeFeedbackRegistrationData recipeFeedbackRegistrationData,
                                           @PathVariable("recipeId") String recipeId, Principal principal)
    {
        recipeFeedbackRegistrationData.setCreatorName(principal.getName());
        recipeFeedbackRegistrationData.setRecipeId(Long.valueOf(recipeId));
        manageRecipeService.registerFeedback(recipeFeedbackRegistrationData);
        return new ResponseEntity(CREATED);
    }

    @PutMapping(value = "/{recipeId}/updateFeedback")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity updateFeedback(@RequestBody RecipeFeedbackRegistrationData recipeFeedbackRegistrationData,
                                           @PathVariable("recipeId") String recipeId, Principal principal)
    {
        recipeFeedbackRegistrationData.setCreatorName(principal.getName());
        recipeFeedbackRegistrationData.setRecipeId(Long.valueOf(recipeId));
        manageRecipeService.updateFeedback(recipeFeedbackRegistrationData);
        return new ResponseEntity(OK);
    }

    @GetMapping(value = "/{recipeId}/getFeedbacks")
    @ResponseStatus(value = HttpStatus.OK)
    public RecipeFeedbackInformationData getRecipeFeedbackInformation(@PathVariable("recipeId") String recipeId)
    {
        return manageRecipeService.getRecipeFeedbackAverageValues(Long.valueOf(recipeId));
    }

    @PostMapping(value = "/{recipeId}/addFavorite")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity addRecipeToFavorite(@PathVariable("recipeId") String recipeId, Principal principal)
    {
        manageRecipeService.addRecipeToFavorite(Long.valueOf(recipeId), principal.getName());
        return new ResponseEntity(CREATED);
    }

    @DeleteMapping(value = "/{recipeId}/removeFavorite")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity removeRecipeFromFavorite(@PathVariable("recipeId") String recipeId, Principal principal)
    {
        manageRecipeService.removeRecipeFromFavorite(Long.valueOf(recipeId), principal.getName());
        return new ResponseEntity(OK);
    }

    @GetMapping(value = "/{recipeId}/isFavorite")
    @ResponseStatus(value = HttpStatus.OK)
    public Boolean verifyIfRecipeIsFavorite(@PathVariable("recipeId") String recipeId, Principal principal)
    {
        return manageRecipeService.verifyIfRecipeIsFavorite(Long.valueOf(recipeId), principal.getName());
    }
}
