package capstone.project.database;

import com.squareup.sqldelight.db.SqlDriver;
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import capstone.project.database.recipe.Database;
import capstone.project.database.recipe.GetIngredients;
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
        List<GetIngredients> ingredients = recipeQueries.getIngredients(boiledEggs.get_id()).executeAsList();

        // Test the primitive is as expected
        assertEquals("Boiled Eggs", boiledEggs.getName());
        assertEquals("Sick Boiled Eggs", boiledEggs.getDescription());
        assertEquals( Category.BREAKFAST, boiledEggs.getCategory() );

        // Test that there are ONLY 2 ingredients per the builder
        assertEquals(2, ingredients.size());
        List<String> ingredientNames = ingredients.stream().map(GetIngredients::getItem_name).collect(Collectors.toList());
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

    @Test
    void testWings()
    {
        // Sub-recipe for the wing sauce
        Recipe wingSauce = new RecipeBuilder(database)
                .name("Wing Sauce")
                .description("Will rek your colon lmao")
                .category(Category.LUNCH)
                .addStep(1L, "Add water to bowl")
                .addStep(2L, "Dump the canned sauce solution in bowl")
                .addStep(3L, "Mix")
                .main(false)
                .insertOrUpdate();

        Recipe wings = new RecipeBuilder(database)
                .name("Chicken Wings")
                .description("Will give you diabeetus")
                .category(Category.LUNCH)
                .addStep(1L, "Unfreeze the meat")
                .addStep(2L, "Grill the wings for 30 min")
                .addStep(3L, "Make the sauce", wingSauce.get_id())
                .addStep(4L, "Coat the wings in the sauce")
                .main(true)
                .insertOrUpdate();

        // Check for 3 steps
        assertEquals(3, database.getRecipeQueries().selectAllRecipeSteps(wingSauce.get_id()).executeAsList().size() );

        // Check for 4 steps
        assertEquals(4, database.getRecipeQueries().selectAllRecipeSteps(wings.get_id()).executeAsList().size() );
    }

    @Test
    void testCereal()
    {
        Recipe cereal = new RecipeBuilder(database)
                .name("Corn Flakes")
                .description("Most important meal of the day!!! :)")
                .category(Category.BREAKFAST)
                .addStep(1L, "get bowl")
                .addStep(2L, "pour milk first")
                .addStep(3L, "pour cereal")
                .addStep(4L, "eat ;-)")
                .main(true)
                .insertOrUpdate();

        // Check for 4 steps
        assertEquals( 4, database.getRecipeQueries().selectAllRecipeSteps(cereal.get_id()).executeAsList().size() );
    }
}
