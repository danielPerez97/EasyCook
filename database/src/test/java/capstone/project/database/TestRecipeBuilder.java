package capstone.project.database;

import com.squareup.sqldelight.db.SqlDriver;
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver;

import static  org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import capstone.project.database.recipe.Database;
import capstone.project.database.recipe.GetIngredients;
import capstone.project.database.recipe.Ingredient;
import capstone.project.database.recipe.IngredientQueries;
import capstone.project.database.recipe.IngredientRecipeQueries;
import capstone.project.database.recipe.Recipe;
import capstone.project.database.recipe.RecipeQueries;
import capstone.project.database.recipe.Step;
import capstone.project.database.recipe.StepsQueries;


public class TestRecipeBuilder
{
    private Database database;
    private SqlDriver driver;
    private IRecipeBuilder builder;
    private Recipe boiledEggs;


    TestRecipeBuilder()
    {
        driver = new JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY, new Properties());
        Database.Companion.getSchema().create(driver);
        database = Database.Companion.invoke( driver, new Recipe.Adapter( CategorySchema.categoryAdapter() ) );
        builder = new RecipeBuilder(database);

        boiledEggs = builder
                .name("Boiled Eggs")
                .description("Sick Boiled Eggs")
                .category( Category.BREAKFAST )
                .addIngredient("Eggs", 3)
                .addIngredient("Water", 8)
                .addStep(1L, "Acquire eggs")
                .addStep(2L, "Boil the eggs")
                .insertOrUpdate();
    }

    // Make this test pass by implementing the RecipeBuilder
    @Test
    public void testBoiledEggs()
    {
        IngredientQueries ingQueries = database.getIngredientQueries();
        RecipeQueries recipeQueries = database.getRecipeQueries();
        StepsQueries stepsQueries = database.getStepsQueries();

        // Boiled eggs is returning null at the moment. Gather all the steps and ingredients that belong to the recipe.
        System.out.println(boiledEggs);
        List<Step> steps = stepsQueries.selectAllRecipeSteps(boiledEggs.get_id()).executeAsList();
        List<GetIngredients> ingredients = recipeQueries.getIngredients(boiledEggs.get_id()).executeAsList();

        assertEquals("Boiled Eggs", boiledEggs.getName());
        assertEquals("Sick Boiled Eggs", boiledEggs.getDescription());
        assertEquals( Category.BREAKFAST, boiledEggs.getCategory() );

        assertTrue(ingredients.size() >= 1);
        ingredients.forEach(ingredient -> assertTrue( Objects.equals(ingredient.getItem_name(), "Eggs") || Objects.equals(ingredient.getItem_name(), "Water")));


        assertTrue(steps.size() >= 1);
        steps.forEach(step -> assertTrue( Objects.equals(step.getDescription(), "Acquire eggs") || Objects.equals(step.getDescription(), "Boil the eggs")));
    }
}
