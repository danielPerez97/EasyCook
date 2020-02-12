package capstone.project.database;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import capstone.project.database.recipe.Database;
import capstone.project.database.recipe.Ingredient;
import capstone.project.database.recipe.Recipe;
import capstone.project.database.recipe.Step;

// Here's a helpful article on the Builder pattern to give you an idea. You shouldn't have to make
// a nested class like the example they use here, RecipeBuilder would be the inner Builder class they
// use in their example
// http://www.informit.com/articles/article.aspx?p=1216151&seqNum=2


public class RecipeBuilder  implements IRecipeBuilder
{
    Database database;

    private  int recipe_id;
    private  String name;
    private  Category category;
    private  String recipeDescription;
    private  int    timeEstimate;
    private  boolean isMain;

    private int stepNumber;
    private String stepDescription;
    private int subRecipeID;


    private int ingredientID;
    private String ingredient;
    private  int ingredientQuantity;


    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;


    RecipeBuilder(Database database)
    {
        recipe_id = -99999999;
        name =  "";
        category = null;
        recipeDescription = "";
        timeEstimate = 0;
        isMain = false;

        ingredients = new ArrayList<Ingredient>();
        steps = new ArrayList<Step>();


        // Use this method! It will cover you and provides a useful error.
        Objects.requireNonNull(database, "database == null");
        this.database = database;
    }

    @Override
    public RecipeBuilder name(String name)
    {
        this.name = name;
        return null;
    }

    @Override
    public RecipeBuilder description(String description)
    {
        this.recipeDescription = description;
        return null;
    }

    @Override
    public RecipeBuilder category(Category category)
    {
        this.category = category;
        return null;
    }

    public RecipeBuilder addIngredient(String ingredient, int ingredientQuantity)
    {
        Random rand = new Random();

        long id = rand.nextInt(2000);

        // How to skip the implementation asking for ID?
        Ingredient currrentIngredient = new Ingredient.Impl(id, ingredient);

        ingredients.add(currrentIngredient);

        return  null;
    }

    @Override
    public RecipeBuilder addStep(Long stepNumber, String stepDescription)
    {


        return null;
    }

    @Override
    public RecipeBuilder addStep(Long stepNumber, String stepDescription, Long optionalRecipeId) {
        return null;
    }

    @Override
    public Recipe insertOrUpdate() {
        return null;
    }
}
