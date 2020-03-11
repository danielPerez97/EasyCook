package capstone.project.easycook.viewmodel;

import androidx.lifecycle.ViewModel;

import com.squareup.sqldelight.runtime.rx.RxQuery;

import javax.inject.Inject;

import capstone.project.core.ViewRecipe;
import capstone.project.database.recipe.Database;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

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

        return RxQuery.mapToOneNonNull(RxQuery.toObservable(database.getRecipeQueries().selectById(id), Schedulers.io()))
                .map(recipe -> new ViewRecipe(recipe.get_id(), recipe.getName()));
    }
}
