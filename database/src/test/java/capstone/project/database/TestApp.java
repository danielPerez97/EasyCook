package capstone.project.database;

import com.squareup.sqldelight.db.SqlDriver;
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import capstone.project.database.recipe.Database;
import capstone.project.database.recipe.IngredientQueries;
import capstone.project.database.recipe.IngredientRecipeQueries;
import capstone.project.database.recipe.Recipe;
import capstone.project.database.recipe.RecipeQueries;
import capstone.project.database.recipe.StepsQueries;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestApp
{
    private Database database;
    private SqlDriver driver;
    private RecipeQueries recipeQueries;
    private StepsQueries  stepsQueries;
    private IngredientQueries ingredientQueries;
    private IngredientRecipeQueries ingredientRecipeQueries;


    TestApp()
    {
        driver = new JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY, new Properties());
        Database.Companion.getSchema().create(driver);

        database = Database.Companion.invoke( driver, new Recipe.Adapter( CategorySchema.categoryAdapter() ) );
        recipeQueries = database.getRecipeQueries();
        stepsQueries  = database.getStepsQueries();
        ingredientQueries = database.getIngredientQueries();
        ingredientRecipeQueries = database.getIngredientRecipeQueries();
    }

    @Test
    void testRecipe()
    {
        // TEST DATA
        String testName         = "Tacos";
        Category testCategory     = Category.BREAKFAST;
        String testDescription  = "This is a test recipe description";
        long testTimeEstimate   = 12000;


        // Empty test
        assertEquals(0, recipeQueries.selectAll().executeAsList().size());

        // Insertion test
        recipeQueries.insert(testName, testCategory, testDescription,testTimeEstimate, 1);

        Recipe recipe = recipeQueries.selectByName(testName).executeAsOne();
        assertEquals(1, recipeQueries.selectAll().executeAsList().size());
        assertEquals(testName, recipeQueries.selectAll().executeAsOne().getName());
        assertEquals(testCategory, recipeQueries.selectAll().executeAsOne().getCategory());
//        assertEquals(testDescription, r);

    }

    @Test
    void testSelectByCategory()
    {

    }

    @Test
    void testStep()
    {
        // TEST DATA
        String testName         = "Tacos";
        Category testCategory     = Category.LUNCH;
        String testDescription  = "THIS IS A TEST DESCRIPTION TEXT";
        int    testTimeEstimate = 10000;
        long   testnumber       = 2000;
        long   testIDNumber     = 1500;


        // Insert random recipe
        recipeQueries.insert(testName, testCategory, testDescription,1200, 1);
        long r_id = recipeQueries.selectAll().executeAsOne().get_id();


        // Empty test
        assertEquals(0, stepsQueries.selectAllRecipeSteps(99999).executeAsList().size());

        // Foreign key test
        stepsQueries.insertOrReplaceSteps(r_id, testnumber, "DESC", null);
        assertEquals(1, stepsQueries.selectAllRecipeSteps(r_id).executeAsList().size());

        // Insertion test
        stepsQueries.insertOrReplaceSteps(2000, testnumber, "STEP 1", null);
        assertEquals(1, stepsQueries.selectAllRecipeSteps(2000).executeAsList().size());

    }

    @Test
    void testIngredient()
    {
        // TEST DATA
        int     testIngredientID    = 100000;
        String  testItemName        = "BANANA";

        // Empty test
        assertEquals(0, ingredientQueries.selectIngredient(testIngredientID).executeAsList().size());

        // Insertion test
        ingredientQueries.insertOrReplaceSteps(testIngredientID, testItemName);
        assertEquals(1, ingredientQueries.selectIngredient(testIngredientID).executeAsList().size());

    }

    @Test
    void testIngredientRecipe()
    {
        //TEST DATA
        long testIngredientAmount = 30;
        long testRecipeID = 10001;
        long testIngredientID = 10002;


        //Empty test
        assertEquals(0, ingredientRecipeQueries.getIngredientAmount(testRecipeID, testIngredientID).executeAsList().size());

        //Insertion test
        ingredientRecipeQueries.insertOrReplaceSteps(testIngredientID, testRecipeID, testIngredientAmount);
        assertEquals(1,ingredientRecipeQueries.getIngredientAmount(testIngredientID, testRecipeID).executeAsList().size());
    }




}
