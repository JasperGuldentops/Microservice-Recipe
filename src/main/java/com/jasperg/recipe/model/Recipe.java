package com.jasperg.recipe.model;

import com.jasperg.recipe.helper.Helper;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "recipes")
public class Recipe {
    @Id
    private String id;
    private String name;
    private Integer cookingTime;
    private String description;
    private String code;

    public Recipe() {
    }

    public Recipe(String name, Integer cookingTime, String description) {
        this.name = name;
        this.cookingTime = cookingTime;
        this.description = description;
        setCode(name);
    }

    public Recipe(String name, Integer cookingTime, String description, String code) {
        this.name = name;
        this.cookingTime = cookingTime;
        this.description = description;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(Integer cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String recipeName) {
        this.code = recipeName + Helper.getRandomString(4);
    }
}
