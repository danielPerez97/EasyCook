package capstone.project.easycook.viewmodel;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import capstone.project.database.Category;
import capstone.project.database.RecipeBuilder;
import capstone.project.database.recipe.Database;
import capstone.project.database.recipe.Recipe;
import capstone.project.easycook.model.ViewIngredient;
import capstone.project.easycook.model.ViewStep;
import io.reactivex.Observable;

public class CreateRecipeViewModel extends ViewModel
{

    private Database database;

    @Inject
    CreateRecipeViewModel(Database database)
    {
        this.database = database;
    }

    public Observable<Recipe> save(String name, String description, Category category, List<ViewIngredient> ingredients, List<ViewStep> steps)
    {
        RecipeBuilder builder = new RecipeBuilder(database)
                .name(name)
                .description(description)
                .category(category)
                .main(true);

        for(ViewIngredient ingredient: ingredients)
        {
            builder = builder.addIngredient(ingredient.getName(), ingredient.getAmount());
        }

        for(ViewStep step: steps)
        {
            builder = builder.addStep(step.getNumber(), step.getDescription());
        }

        return Observable.just( builder.insertOrUpdate() );
    }
}