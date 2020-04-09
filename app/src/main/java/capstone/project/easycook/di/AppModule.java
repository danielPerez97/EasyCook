// Dummy dependency injector, this will serve as an example to copy from

package capstone.project.easycook.di;

import android.content.Context;

import com.squareup.moshi.Moshi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import capstone.project.core.ViewRecipe;
import capstone.project.easycook.model.ViewIngredient;
import capstone.project.easycook.model.ViewStep;
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
    public Context provideContext()
    {
        return context;
    }

    @Provides
    @Singleton
    public List<ViewRecipe> provideDummyRecipes()
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

    @Provides
    @Singleton
    public List<ViewIngredient> provideDummyIngredients()
    {
        ArrayList<ViewIngredient> ingredients = new ArrayList<>();
        ingredients.add(new ViewIngredient("Salt", "To Taste"));
        ingredients.add(new ViewIngredient("Pepper", "To Taste"));
        ingredients.add(new ViewIngredient("Anaheim Peppers", "12"));
        return ingredients;
    }

    @Provides
    @Singleton
    public List<ViewStep> provideDummySteps()
    {
        ArrayList<ViewStep> steps = new ArrayList<>();
        steps.add(new ViewStep(1, "add salt"));
        steps.add(new ViewStep(2, "add pepper"));
        return steps;
    }

    @Provides
    @Singleton
    Moshi provideMoshi()
    {
        return new Moshi.Builder().build();
    }
}
