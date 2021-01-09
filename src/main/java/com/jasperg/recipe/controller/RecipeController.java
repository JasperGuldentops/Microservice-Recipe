package com.jasperg.recipe.controller;

import com.jasperg.recipe.model.Recipe;
import com.jasperg.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

//    @PostConstruct
//    public void fillDB() {
//        if(recipeRepository.count() == 0) {
//            recipeRepository.save(new Recipe("Pizza", 45, "Roll dough, bake, ready", "jg@gmail.com-0000", "0000"));
//            recipeRepository.save(new Recipe("Fries", 30, "Slice potato, fry, ready", "jg@gmail.com-0000", "0000"));
//            recipeRepository.save(new Recipe("Milkshake", 20, "Milk cow, shake, ready", "aa@gmail.com-0000", "0000"));
//            recipeRepository.save(new Recipe("Vanilla Ice", 120, "Freeze milk, ready", "bb@gmail.com-0000", "0000"));
//            recipeRepository.save(new Recipe("Spaghetti", 45, "Cook pasta, add sauce, ready", "cc@gmail.com-0000", "0000"));
//        }
//
//        System.out.println("Recipe test: " + recipeRepository.findAll().size());
//    }

    @GetMapping("/recipes")
    public List<Recipe> GetRecipes() {

        return recipeRepository.findAll();
    }

    @GetMapping("/recipes/name/{name}")
    public List<Recipe> GetRecipesByName(@PathVariable String name) {

        return recipeRepository.findRecipesByNameContaining(name);
    }

    @GetMapping("/recipes/userCode/{userCode}")
    public List<Recipe> GetRecipesByUserCode(@PathVariable String userCode) {

        return recipeRepository.findRecipesByUserCode(userCode);
    }

    @GetMapping("/recipes/code/{code}")
    public Recipe GetRecipeByCode(@PathVariable String code) {

        return recipeRepository.findRecipeByCode(code);
    }

    @PostMapping("/recipes")
    public Recipe addRecipe (@RequestBody Recipe recipe) {

        recipeRepository.save(recipe);

        return recipe;
    }

    @PutMapping("/recipes")
    public Recipe updateRecipe(@RequestBody Recipe updatedRecipe){
        Recipe recipe = recipeRepository.findRecipeByCode(updatedRecipe.getCode());

        recipe.setName(updatedRecipe.getName());
        recipe.setDescription(updatedRecipe.getDescription());
        recipe.setCookingTime(updatedRecipe.getCookingTime());

        recipeRepository.save(recipe);

        return recipe;
    }

    @DeleteMapping("/recipes/{code}")
    public ResponseEntity deleteRecipe(@PathVariable String code){
        Recipe recipe = recipeRepository.findRecipeByCode(code);
        if(recipe!=null){
            recipeRepository.delete(recipe);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
