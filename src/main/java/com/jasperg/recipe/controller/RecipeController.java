package com.jasperg.recipe.controller;

import com.jasperg.recipe.model.Recipe;
import com.jasperg.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @PostConstruct
    public void fillDB() {
        if(recipeRepository.count() == 0) {
            recipeRepository.save(new Recipe("Pizza", 45, "Roll dough, bake, ready"));
            recipeRepository.save(new Recipe("Fries", 30, "Slice potato, fry, ready"));
            recipeRepository.save(new Recipe("Milkshake", 20, "Milk cow, shake, ready"));
            recipeRepository.save(new Recipe("Vanilla Ice", 120, "Freeze milk, ready"));
            recipeRepository.save(new Recipe("Spaghetti", 45, "Cook pasta, add sauce, ready"));
        }

        System.out.println("Recipe test: " + recipeRepository.findAll().size());
    }

    @GetMapping("/recipes")
    public List<Recipe> GetRecipeByName() {

        return recipeRepository.findAll();
    }

    @GetMapping("/recipes/{name}")
    public Recipe GetRecipeByName(@PathVariable String name) {

        return recipeRepository.findRecipeByName(name);
    }

    @PostMapping("/recipes")
    public Recipe addRecipe (@RequestBody Recipe recipe) {

        recipeRepository.save(recipe);

        return recipe;
    }

    @PutMapping("/recipes")
    public Recipe updateRecipe(@RequestBody Recipe updatedRecipe){
        Recipe recipe = recipeRepository.findRecipeByName(updatedRecipe.getName());

        recipe.setDescription(updatedRecipe.getDescription());
        recipe.setCookingTime(updatedRecipe.getCookingTime());

        recipeRepository.save(recipe);

        return recipe;
    }

    @DeleteMapping("/recipes/{name}")
    public ResponseEntity<Object> deleteRecipe(@PathVariable String name){
        Recipe recipe = recipeRepository.findRecipeByName(name);
        if(recipe!=null){
            recipeRepository.delete(recipe);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
