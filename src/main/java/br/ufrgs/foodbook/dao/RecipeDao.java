package br.ufrgs.foodbook.dao;

import br.ufrgs.foodbook.model.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeDao extends JpaRepository<Recipe, Long>
{
    Recipe findByName(String recipeName);
}
