package capstone.project.easycook.di;

import javax.inject.Singleton;

import capstone.project.database.recipe.Database;
import capstone.project.database.recipe.RecipeQueries;
import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule
{
    private Database database;

    public DatabaseModule(Database database)
    {

        this.database = database;
    }


    @Provides
    @Singleton
    public Database provideDatabase()
    {
        return database;
    }
}
