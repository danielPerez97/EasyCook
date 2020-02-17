package capstone.project.database;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;

import capstone.project.database.recipe.Database;
import capstone.project.database.recipe.Recipe;

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
    private  long isMain;

    private long stepNumber;
    private String stepDescription;
    private long subRecipeID;


    private long ingredientID;
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
        isMain = 0;

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
        return this;
    }

    @Override
    public RecipeBuilder description(String description)
    {
        this.recipeDescription = description;
        return this;
    }

    @Override
    public RecipeBuilder category(Category category)
    {
        this.category = category;
        return this;
    }

    public RecipeBuilder addIngredient(String ingredient, int ingredientQuantity)
    {
        Ingredient currentIngrendient = new Ingredient(ingredient, ingredientQuantity);

        ingredients.add(currentIngrendient);

        return this;
    }

    @Override
    public RecipeBuilder addStep(Long stepNumber, String stepDescription)
    {
        Step currentStep = new Step(stepNumber, stepDescription);

        steps.add(currentStep);

        return this;
    }

    @Override
    public RecipeBuilder addStep(Long stepNumber, String stepDescription, Long optionalRecipeId)
    {
        Step currentStep = new Step(stepNumber, stepDescription, optionalRecipeId);

        steps.add(currentStep);

        return this;
    }

    @Override
    public RecipeBuilder main(boolean isMain)
    {
        Objects.requireNonNull(isMain);

        if(isMain == false)
            this.isMain = 0;
        else
            this.isMain = 1;
        return this;
    }

    @Override
    public Recipe insertOrUpdate()
    {
        Objects.requireNonNull(name, "name is null");
        Objects.requireNonNull(isMain, "isMain is null");

        if (category == null) { category = Category.UNKNOWN; }

        database.getRecipeQueries().insert(name, category, recipeDescription, isMain);

        final Recipe recipe = database.getRecipeQueries().selectByName(name).executeAsOne();

        ingredients.forEach( ingredient -> {
            database.getIngredientQueries().insertOrReplace(ingredient.getName());
            final capstone.project.database.recipe.Ingredient dbIngredient = database.getIngredientQueries().selectByName(ingredient.getName()).executeAsOne();
            database.getIngredientRecipeQueries().insert(ingredient.getId(), recipe.get_id(), ingredient.getAmount().longValue());
        });

        steps.forEach( step -> {
           database.getStepsQueries().insertOrReplaceSteps(recipe_id, stepNumber, stepDescription, subRecipeID);
        });

        return recipe;
    }
}