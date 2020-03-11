package capstone.project.easycook;

import android.app.Application;

import com.squareup.sqldelight.android.AndroidSqliteDriver;

import capstone.project.database.CategorySchema;
import capstone.project.database.recipe.Database;
import capstone.project.database.recipe.Recipe;
import capstone.project.easycook.di.AppInjector;
import capstone.project.easycook.di.AppModule;
import capstone.project.easycook.di.DaggerAppInjector;
import capstone.project.easycook.di.DatabaseModule;

public class BaseApplication extends Application
{
    private AppInjector injector;

    @Override
    public void onCreate()
    {
        super.onCreate();

        AndroidSqliteDriver driver = new AndroidSqliteDriver(Database.Companion.getSchema(), getApplicationContext(), "Recipes.db");

        injector = DaggerAppInjector.builder()
                .appModule( new AppModule( getApplicationContext() ) )
                .databaseModule( new DatabaseModule( Database.Companion.invoke( driver, new Recipe.Adapter( CategorySchema.categoryAdapter() ) ) ) )
                .build();
    }

    public AppInjector injector()
    {
        return injector;
    }
}
