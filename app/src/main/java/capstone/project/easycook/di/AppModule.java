// Dummy dependency injector, this will serve as an example to copy from

package capstone.project.easycook.di;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import capstone.project.easycook.model.ViewRecipe;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule
{
    private Context context;

    public AppModule(Context context)
    {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext()
    {
        return context;
    }

    @Provides
    @Singleton
    List<ViewRecipe> provideDummyRecipes()
    {
        ArrayList<ViewRecipe> recipes = new ArrayList<>();
        recipes.add(new ViewRecipe(-1, "Cereal"));
        recipes.add(new ViewRecipe(-1, "Eggs Con Chorizo"));
        recipes.add(new ViewRecipe(-1, "Scramble Eggs with Ketchup"));
        recipes.add(new ViewRecipe(-1, "Grilled Cheeseburgers"));
        recipes.add(new ViewRecipe(-1, "Fish and Chips"));
        recipes.add(new ViewRecipe(-1, "Grilled Cheese Sandwich"));
        recipes.add(new ViewRecipe(-1, "Gnocchi Soup"));
        recipes.add(new ViewRecipe(-1, "Spaghetti"));
        return recipes;
    }
}
