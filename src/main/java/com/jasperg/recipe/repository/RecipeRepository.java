package com.jasperg.recipe.repository;

import com.jasperg.recipe.model.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String> {
    Recipe findRecipeByCode(String code);
    List<Recipe> findAll();
    List<Recipe> findRecipesByNameContaining(String name);

}
