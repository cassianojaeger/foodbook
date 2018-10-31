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

@RestController
@RequestMapping("/secured/manage/recipe")
public class ManageRecipesController extends AbstractGenericController
{
    @Resource
    ManageRecipeService manageRecipeService;

    @PostMapping(value = "/giveFeedback")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity registerFeedback(@RequestBody RecipeFeedbackRegistrationData recipeFeedbackRegistrationData, Principal principal)
    {
        recipeFeedbackRegistrationData.setCreatorName(principal.getName());
        manageRecipeService.registerFeedback(recipeFeedbackRegistrationData);
        return new ResponseEntity(CREATED);
    }

    @GetMapping(value = "/{recipeId}")
    @ResponseStatus(value = HttpStatus.OK)
    public RecipeFeedbackInformationData getRecipeFeedbackInformation(@PathVariable("recipeId") String recipeId)
    {
        return manageRecipeService.getRecipeFeedbackAverageValues(Long.valueOf(recipeId));
    }
}
