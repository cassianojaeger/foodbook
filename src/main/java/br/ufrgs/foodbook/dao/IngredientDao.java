package br.ufrgs.foodbook.dao;

import br.ufrgs.foodbook.model.recipe.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientDao extends CrudRepository<Ingredient, Integer>
{
    Ingredient findByName(String ingredientName);
}
