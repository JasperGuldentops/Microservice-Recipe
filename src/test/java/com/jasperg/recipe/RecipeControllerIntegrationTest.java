package com.jasperg.recipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasperg.recipe.model.Recipe;
import com.jasperg.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecipeRepository recipeRepository;

    private ObjectMapper mapper = new ObjectMapper();

    private Recipe recipe1 = new Recipe("Pizza", 45, "Roll dough, bake, ready",
            "jg@gmail.com-0000", "Pizza-0000");
    private Recipe recipe2 = new Recipe("Fries", 30, "Slice potato, fry, ready",
            "jg@gmail.com-0000", "Fries-0000");
    private Recipe recipeToChange = new Recipe("Milkshake", 20, "Milk cow, shake, ready",
            "aa@gmail.com-0000", "Milkshake-0000");
    private Recipe recipeToDelete = new Recipe("Spaghetti", 45, "Cook pasta, add sauce, ready",
            "cc@gmail.com-0000", "Spaghetti-0000");

    @BeforeEach
    public void beforeAllTests() {

        recipeRepository.deleteAll();
        recipeRepository.save(recipe1);
        recipeRepository.save(recipe2);
        recipeRepository.save(recipeToChange);
        recipeRepository.save(recipeToDelete);
    }

    @AfterEach
    public void afterAllTests() {

        recipeRepository.deleteAll();
    }

    @Test
    public void givenRecipe_whenGetAllRecipes_thenReturnJsonRecipes() throws Exception {

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);
        recipes.add(recipeToChange);
        recipes.add(recipeToDelete);

        mockMvc.perform(get("/recipes"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))

                .andExpect(jsonPath("$[0].name", is("Pizza")))
                .andExpect(jsonPath("$[0].cookingTime", is(45)))
                .andExpect(jsonPath("$[0].description", is("Roll dough, bake, ready")))
                .andExpect(jsonPath("$[0].userCode", is("jg@gmail.com-0000")))
                .andExpect(jsonPath("$[0].code", is("Pizza-0000")))

                .andExpect(jsonPath("$[1].name", is("Fries")))
                .andExpect(jsonPath("$[1].cookingTime", is(30)))
                .andExpect(jsonPath("$[1].description", is("Slice potato, fry, ready")))
                .andExpect(jsonPath("$[1].userCode", is("jg@gmail.com-0000")))
                .andExpect(jsonPath("$[1].code", is("Fries-0000")))

                .andExpect(jsonPath("$[2].name", is("Milkshake")))
                .andExpect(jsonPath("$[2].cookingTime", is(20)))
                .andExpect(jsonPath("$[2].description", is("Milk cow, shake, ready")))
                .andExpect(jsonPath("$[2].userCode", is("aa@gmail.com-0000")))
                .andExpect(jsonPath("$[2].code", is("Milkshake-0000")))

                .andExpect(jsonPath("$[3].name", is("Spaghetti")))
                .andExpect(jsonPath("$[3].cookingTime", is(45)))
                .andExpect(jsonPath("$[3].description", is("Cook pasta, add sauce, ready")))
                .andExpect(jsonPath("$[3].userCode", is("cc@gmail.com-0000")))
                .andExpect(jsonPath("$[3].code", is("Spaghetti-0000")));
    }

    @Test
    public void givenRecipe_whenGetRecipesByName_thenReturnJsonRecipes() throws Exception {

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipeToDelete);

        mockMvc.perform(get("/recipes/name/{name}", "p"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))

                .andExpect(jsonPath("$[0].name", is("Spaghetti")))
                .andExpect(jsonPath("$[0].cookingTime", is(45)))
                .andExpect(jsonPath("$[0].description", is("Cook pasta, add sauce, ready")))
                .andExpect(jsonPath("$[0].userCode", is("cc@gmail.com-0000")))
                .andExpect(jsonPath("$[0].code", is("Spaghetti-0000")));
    }

    @Test
    public void givenRecipe_whenGetRecipesByUserCode_thenReturnJsonRecipes() throws Exception {

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        mockMvc.perform(get("/recipes/userCode/{userCode}", "jg@gmail.com-0000"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].name", is("Pizza")))
                .andExpect(jsonPath("$[0].cookingTime", is(45)))
                .andExpect(jsonPath("$[0].description", is("Roll dough, bake, ready")))
                .andExpect(jsonPath("$[0].userCode", is("jg@gmail.com-0000")))
                .andExpect(jsonPath("$[0].code", is("Pizza-0000")))

                .andExpect(jsonPath("$[1].name", is("Fries")))
                .andExpect(jsonPath("$[1].cookingTime", is(30)))
                .andExpect(jsonPath("$[1].description", is("Slice potato, fry, ready")))
                .andExpect(jsonPath("$[1].userCode", is("jg@gmail.com-0000")))
                .andExpect(jsonPath("$[1].code", is("Fries-0000")));
    }

    @Test
    public void givenRecipe_whenGetRecipeByCode_thenReturnJsonRecipe() throws Exception {

        mockMvc.perform(get("/recipes/code/{code}", "Pizza-0000"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.name", is("Pizza")))
                .andExpect(jsonPath("$.cookingTime", is(45)))
                .andExpect(jsonPath("$.description", is("Roll dough, bake, ready")))
                .andExpect(jsonPath("$.userCode", is("jg@gmail.com-0000")))
                .andExpect(jsonPath("$.code", is("Pizza-0000")));
    }

    @Test
    public void whenPostRecipe_thenReturnJsonRecipe() throws Exception {

        Recipe newRecipe = new Recipe("Vanilla Ice", 120, "Freeze milk, ready",
                "cc@gmail.com-0000", "Vanilla Ice-0000");

        mockMvc.perform(post("/recipes")
                .content(mapper.writeValueAsString(newRecipe))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.name", is("Vanilla Ice")))
                .andExpect(jsonPath("$.cookingTime", is(120)))
                .andExpect(jsonPath("$.description", is("Freeze milk, ready")))
                .andExpect(jsonPath("$.userCode", is("cc@gmail.com-0000")))
                .andExpect(jsonPath("$.code", is("Vanilla Ice-0000")));
    }

    @Test
    public void whenPutRecipe_thenReturnJsonRecipe() throws Exception {

        Recipe updateRecipe = new Recipe("Goat Milkshake", 25, "Milk goat, shake, ready",
                "aa@gmail.com-0000", "Milkshake-0000");

        mockMvc.perform(put("/recipes")
                .content(mapper.writeValueAsString(updateRecipe))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.name", is("Goat Milkshake")))
                .andExpect(jsonPath("$.cookingTime", is(25)))
                .andExpect(jsonPath("$.description", is("Milk goat, shake, ready")))
                .andExpect(jsonPath("$.userCode", is("aa@gmail.com-0000")))
                .andExpect(jsonPath("$.code", is("Milkshake-0000")));
    }

    @Test
    public void givenRecipe_whenDeleteRecipe_thenStatusOk() throws Exception {

        mockMvc.perform(delete("/recipes/{code}", "Spaghetti-0000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenRecipe_whenDeleteRecipe_thenStatusNotFound() throws Exception {

        mockMvc.perform(delete("/recipes/{code}", "Not a real code")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
