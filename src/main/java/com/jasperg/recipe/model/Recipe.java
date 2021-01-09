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
    private String userCode;
    private String code;

    public Recipe() {
    }

    public Recipe(String name, Integer cookingTime, String description, String userCode) {
        this.name = name;
        this.cookingTime = cookingTime;
        this.description = description;
        this.userCode = userCode;
        this.code = name + "-" + Helper.getRandomString(4);
    }

    public Recipe(String name, Integer cookingTime, String description, String userCode, String code) {
        this.name = name;
        this.cookingTime = cookingTime;
        this.description = description;
        this.userCode = userCode;
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

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
