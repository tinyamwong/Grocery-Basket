package edu.wit.mobileapp.grocerybasket;


import java.util.ArrayList;
import java.util.Objects;

public class Recipe
{
    // unique ID given to each recipe
    private int recipeID;

    // name of the recipe
    private String name;

    // image of the recipe
    private byte[] image;

    // text of the description
    private String description;

    // text of the instruction
    private String instruction;

    // name of the chef (credits)
    private String chef;

    // # of people recipe serves
    private String serves;

    // all of the ingredients in the recipe
    private ArrayList<RecipeIngredient> ingredients;

    //multiplier for recipe
    private int multiplier;

    public Recipe() {}

    public Recipe(int recipeID, String name, byte[] image, String description, String instruction, String chef, String serves)
    {
        //didn't want to add to constructor
        multiplier = 0;
        this.recipeID = recipeID;
        this.name = name;
        this.image = image;
        this.description = description;
        this.instruction = instruction;
        this.chef = chef;
        this.serves = serves;
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Recipe recipe = (Recipe) o;
        return recipeID == recipe.recipeID;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(recipeID);
    }

    public byte[] getImage()
    {
        return image;
    }

    public void setImage(byte[] image)
    {
        this.image = image;
    }

    public int getMultiplier()
    {
        return multiplier;
    }

    public void setMultiplier(int multiplier)
    {
        this.multiplier = multiplier;
    }

    public String getServes()
    {
        return serves;
    }

    public void setServes(String serves)
    {
        this.serves = serves;
    }
}
