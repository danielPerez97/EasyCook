package capstone.project.easycook.viewmodel;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import capstone.project.core.ViewRecipe;
import capstone.project.database.recipe.Database;
import io.reactivex.Observable;

public class RecipeViewModel extends ViewModel
{
    private Database database;

    @Inject
    RecipeViewModel(Database database)
    {
        this.database = database;
    }

    public Observable<ViewRecipe> getRecipe(long id)
    {
        return Observable.just( new ViewRecipe(-1L, "Waffles") );
    }
}
