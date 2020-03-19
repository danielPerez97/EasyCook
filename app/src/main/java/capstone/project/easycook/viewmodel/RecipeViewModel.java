package capstone.project.easycook.viewmodel;

import androidx.lifecycle.ViewModel;

import com.squareup.sqldelight.runtime.rx.RxQuery;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import capstone.project.core.ViewRecipe;
import capstone.project.database.recipe.Database;
import capstone.project.database.recipe.Step;
import capstone.project.easycook.model.ViewStep;
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

    public Observable<List<ViewStep>> getSteps(long recipeId)
    {
        return RxQuery.mapToList( RxQuery.toObservable( database.getStepsQueries().getStepsFromRecipeId(recipeId) ) )
                .map((List<Step> steps) -> {
                    ArrayList<ViewStep> viewSteps = new ArrayList<>();
                    for(Step step: steps)
                    {
                        viewSteps.add(new ViewStep(step.getStep_number(), step.getDescription()));
                    }
                    return viewSteps;
                });
    }
}
