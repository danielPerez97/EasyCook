package capstone.project.database;

import java.util.ArrayList;
import java.util.Objects;

import capstone.project.database.recipe.Database;
import capstone.project.database.recipe.Recipe;


@SuppressWarnings("WeakerAccess")
public class RecipeBuilder implements IRecipeBuilder
{
    Database database;

    private  int recipe_id;
    private  String name;
    private  Category category;
    private  String recipeDescription;
    private  int    timeEstimate;
    private  Long isMain;

    private long stepNumber;
    private String stepDescription;
    private long subRecipeID;


    private long ingredientID;
    private String ingredient;
    private  int ingredientQuantity;


    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;


    public RecipeBuilder(Database database)
    {
        recipe_id = -99999999;
        name =  null;
        category = null;
        recipeDescription = "";
        timeEstimate = 0;
        isMain = null;

        ingredients = new ArrayList<>();
        steps = new ArrayList<>();


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

    public RecipeBuilder addIngredient(String ingredient, String amount)
    {
        Ingredient currentIngrendient = new Ingredient(ingredient, amount);

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
    public RecipeBuilder main(Boolean isMain)
    {
        Objects.requireNonNull(isMain);

        if(!isMain)
            this.isMain = 0L;
        else
            this.isMain = 1L;
        return this;
    }

    @Override
    public Recipe insertOrUpdate()
    {
        // Check for null + handle null category
        Objects.requireNonNull(name, "name is null");
        Objects.requireNonNull(isMain, "isMain is null");
        if (category == null) { category = Category.UNKNOWN; }

        // Insert the recipe and yank it back out from the database
        database.getRecipeQueries().insert(name, category, recipeDescription, isMain);
        final Recipe recipe = database.getRecipeQueries().selectByName(name).executeAsOne();

        // Insert the ingredients associated with the recipe
        for(Ingredient ingredient: ingredients)
        {
            database.getIngredientQueries().insertOrReplace(ingredient.getName());
            final capstone.project.database.recipe.Ingredient dbIngredient = database.getIngredientQueries().selectByName(ingredient.getName()).executeAsOne();
            database.getIngredientRecipeQueries().insert(dbIngredient.getI_id(), recipe.get_id(), Objects.requireNonNull(ingredient.getAmount()));
        }

        // Insert the steps associated with the recipe
        for(Step step: steps)
        {
            database.getStepsQueries().insertOrReplaceSteps(recipe.get_id(), step.getNumber(), step.getDesc(), step.getExternalRecipeId());
        }

        return recipe;
    }
}