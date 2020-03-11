package capstone.project.easycook.di;

import javax.inject.Singleton;

import capstone.project.database.recipe.Database;
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
    Database provideDatabase()
    {
        return database;
    }
}
