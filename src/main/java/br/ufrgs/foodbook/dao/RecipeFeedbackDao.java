package br.ufrgs.foodbook.dao;

import br.ufrgs.foodbook.model.recipe.Recipe;
import br.ufrgs.foodbook.model.recipe.RecipeFeedback;
import br.ufrgs.foodbook.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeFeedbackDao extends JpaRepository<RecipeFeedback, Long>
{
    List<RecipeFeedback> findAllByUser(User user);
    List<RecipeFeedback> findAllByRecipe(Recipe recipe);
    RecipeFeedback findByRecipeAndUser(Recipe recipe, User user);
}
