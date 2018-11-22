package br.ufrgs.foodbook.dao;

import br.ufrgs.foodbook.model.recipe.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeDao extends JpaRepository<Recipe, Long>
{
    Recipe findByName(String recipeName);
    Page<Recipe> findAllById(Long recipeId, Pageable pageable);
    List<Recipe> findByNameIgnoreCaseContaining(String recipeName);

    @Query(value = "Select * FROM recipe LEFT JOIN favorite_recipes favRecipe on recipe.id = favRecipe.recipe_id LEFT JOIN user_ u on favRecipe.user_id = u.id WHERE u.user_name = :username",
           countQuery = "SELECT count(*) FROM recipe",
           nativeQuery = true
    )
    Page<Recipe> findFavoriteRecipes(@Param("username") String username, Pageable pageable);
}
