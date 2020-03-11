package capstone.project.easycook.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import capstone.project.database.recipe.Database
import capstone.project.easycook.model.ViewStep
import com.squareup.sqldelight.runtime.rx.asObservable
import com.squareup.sqldelight.runtime.rx.mapToList
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CookRecipeViewModel @Inject internal constructor(private val database: Database): ViewModel()
{
    fun steps(recipeId: Long): Observable<List<ViewStep>>
    {
        return database.recipeQueries.selectAllRecipeSteps(recipeId)
            .asObservable(Schedulers.io())
            .mapToList()
            .doOnNext { Log.i("CookRecipeViewModel", it.size.toString()) }
            .map { dbStepList -> dbStepList.map { ViewStep(it.step_number, it.description) } }
    }

}