package br.ufrgs.foodbook.controller;

import br.ufrgs.foodbook.model.recipe.Recipe;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/secured/recipe")
public class RecipeController
{

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Set<Recipe> getRecipes() {
        return (Set<Recipe>) new Recipe();
    }

}
