package br.ufrgs.foodbook.dao;

import br.ufrgs.foodbook.model.groups.Group;
import br.ufrgs.foodbook.model.recipe.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeDao extends JpaRepository<Recipe, Long>
{
    Page<Recipe> findByGroup(Group group, Pageable pageable);
    Page<Recipe> findAllById(Long recipeId, Pageable pageable);
    Page<Recipe> findByNameIgnoreCaseContaining(String recipeName, Pageable pageable);

    @Query(value = "Select * FROM recipe LEFT JOIN favorite_recipes favRecipe on recipe.id = favRecipe.recipe_id LEFT JOIN user_ u on favRecipe.user_id = u.id WHERE u.user_name = :username",
           countQuery = "SELECT count(*) FROM recipe",
           nativeQuery = true
    )
    Page<Recipe> findFavoriteRecipes(@Param("username") String username, Pageable pageable);
}
