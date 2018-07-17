package edu.wit.mobileapp.mealprepplanner;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Recipe
{
    // unique ID given to each recipe
    private int recipeID;

    // name of the recipe
    private String name;

    // image of the recipe
    private Bitmap image;

    // text of the description
    private String description;

    // text of the instruction
    private String instruction;

    // name of the chef (credits)
    private String chef;

    // all of the ingredients in the recipe
    private ArrayList<RecipeIngredient> ingredients;

    public Recipe() {}

    public Recipe(int recipeID, String name, Bitmap image, String description, String instruction, String chef)
    {
        this.recipeID = recipeID;
        this.name = name;
        this.image = image;
        this.description = description;
        this.instruction = instruction;
        this.chef = chef;
        // XXX this is to avoid headaches - each instances of Recipe will have automatically have an ArrayList for ingredients created
        this.ingredients = new ArrayList<>();
    }

    public String getInstruction()
    {
        return instruction;
    }

    public void setInstruction(String instruction)
    {
        this.instruction = instruction;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getRecipeID()
    {
        return recipeID;
    }

    public void setRecipeID(int recipeID)
    {
        this.recipeID = recipeID;
    }

    public ArrayList<RecipeIngredient> getIngredients()
    {
        return ingredients;
    }

    public void setIngredients(ArrayList<RecipeIngredient> ingredients)
    {
        this.ingredients = ingredients;
    }

    public String getChef()
    {
        return chef;
    }

    public void setChef(String chef)
    {
        this.chef = chef;
    }

    @Override
    public String toString()
    {
        return "Recipe{" +
                "recipeID=" + recipeID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", instruction='" + instruction + '\'' +
                ", chef='" + chef + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }

    public Bitmap getImage()
    {
        return image;
    }

    public void setImage(Bitmap image)
    {
        this.image = image;
    }
}
