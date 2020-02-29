package capstone.project.database;

import com.squareup.sqldelight.db.SqlDriver;
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import capstone.project.database.recipe.Database;
import capstone.project.database.recipe.Ingredient;
import capstone.project.database.recipe.Ingredientrecipe;
import capstone.project.database.recipe.Recipe;
import capstone.project.database.recipe.RecipeQueries;
import capstone.project.database.recipe.Step;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class TestRecipeBuilder
{
    private Database database;
    private SqlDriver driver;
    private Recipe boiledEggs;


    TestRecipeBuilder()
    {
        driver = new JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY, new Properties());
        Database.Companion.getSchema().create(driver);
        database = Database.Companion.invoke( driver, new Recipe.Adapter( CategorySchema.categoryAdapter() ) );

        boiledEggs = new RecipeBuilder(database)
                .name("Boiled Eggs")
                .main(true)
                .description("Sick Boiled Eggs")
                .category( Category.BREAKFAST )
                .addIngredient("Eggs", "3")
                .addIngredient("Water", "8")
                .addStep(1L, "Acquire eggs")
                .addStep(2L, "Boil the eggs")
                .insertOrUpdate();
    }

    // Make this test pass by implementing the RecipeBuilder
    @Test
    void testBoiledEggs()
    {
        RecipeQueries recipeQueries = database.getRecipeQueries();

        // Gather the steps and the ingredients into lists
        List<Step> steps = recipeQueries.selectAllRecipeSteps(boiledEggs.get_id()).executeAsList();
        List<Ingredient> ingredients = recipeQueries.getIngredients(boiledEggs.get_id()).executeAsList();

        // Test the primitive is as expected
        assertEquals("Boiled Eggs", boiledEggs.getName());
        assertEquals("Sick Boiled Eggs", boiledEggs.getDescription());
        assertEquals( Category.BREAKFAST, boiledEggs.getCategory() );

        // Test that there are ONLY 2 ingredients per the builder
        assertEquals(2, ingredients.size());
        List<String> ingredientNames = ingredients.stream().map(Ingredient::getItem_name).collect(Collectors.toList());
        assertTrue(ingredientNames.contains("Eggs"));
        assertTrue(ingredientNames.contains("Water"));

        // Test that there are ONLY 2 steps per the builder
        assertEquals(2, steps.size());
        List<String> stepDescriptions = steps.stream().map(Step::getDescription).collect(Collectors.toList());
        assertTrue(stepDescriptions.contains("Acquire eggs"));
        assertTrue(stepDescriptions.contains("Boil the eggs"));

        // Test the amounts
        List<Ingredientrecipe> ingredientrecipes = recipeQueries.selectAllIngredientRecipes(boiledEggs.get_id()).executeAsList();
        List<String> amounts = ingredientrecipes.stream().map(Ingredientrecipe::getAmount).collect(Collectors.toList());
        assertEquals(ingredientrecipes.size(), 2);
        assertEquals(amounts.size(), 2);
        assertTrue(amounts.contains("3"));
        assertTrue(amounts.contains("8"));
    }

    @Test
    void testFailures()
    {
        // Test that an exception IS thrown when there is no name set
        RecipeBuilder testNameBuilder = new RecipeBuilder(database);
        assertThrows(Exception.class, () -> testNameBuilder.main(true).insertOrUpdate());

        // Test that an exception IS thrown when there is 'main' property set
        RecipeBuilder testIsMainBuilder = new RecipeBuilder(database);
        assertThrows(Exception.class, () -> testIsMainBuilder.name("Test").insertOrUpdate());

        // Test that without setting a category, it becomes Category.UNKNOWN
        Recipe categoryTestBuilder = new RecipeBuilder(database)
                .name("Test")
                .main(true)
                .insertOrUpdate();
        assertSame(categoryTestBuilder.getCategory(), Category.UNKNOWN);
    }
}
