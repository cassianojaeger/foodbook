package br.ufrgs.foodbook.strategies.populator.impl;

import br.ufrgs.foodbook.dao.RecipeDao;
import br.ufrgs.foodbook.dto.recipe.RecipeFeedbackRegistrationData;
import br.ufrgs.foodbook.model.recipe.Recipe;
import br.ufrgs.foodbook.model.recipe.RecipeFeedback;
import br.ufrgs.foodbook.strategies.populator.Populator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class RecipeFeedbackRecipePopulator implements Populator<RecipeFeedbackRegistrationData, RecipeFeedback>
{
    @Resource
    RecipeDao recipeDao;

    @Override
    public void populate(RecipeFeedbackRegistrationData source, RecipeFeedback target)
    {
        Optional<Recipe> oRecipe = recipeDao.findById(source.getRecipeId());
        oRecipe.ifPresent(target::setRecipe);
    }

}
