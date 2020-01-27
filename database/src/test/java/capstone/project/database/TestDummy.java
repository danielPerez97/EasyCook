package capstone.project.database;

import com.squareup.sqldelight.db.SqlDriver;
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import capstone.project.database.recipe.Database;
import capstone.project.database.recipe.DummyQueries;
import capstone.project.database.recipe.Recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Example class to demonstrate a simple test and setting up for testing
// Remember, this module has no knowledge of Android, we'll set it up in the app
// module once pieces come together and are ready.
class TestDummy
{
    private SqlDriver driver;
    private Database database;
    private DummyQueries queries;

    TestDummy()
    {
         driver = new JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY, new Properties());
         database = Database.Companion.invoke(driver, new Recipe.Adapter( CategorySchema.categoryAdapter() ));
         Database.Companion.getSchema().create(driver);
         queries = database.getDummyQueries();
    }


}
