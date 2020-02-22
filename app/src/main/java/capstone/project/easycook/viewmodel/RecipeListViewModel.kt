package capstone.project.easycook.viewmodel


import androidx.lifecycle.ViewModel
import capstone.project.database.Category
import capstone.project.database.recipe.Database
import capstone.project.easycook.model.ViewRecipe
import com.squareup.sqldelight.runtime.rx.asObservable
import com.squareup.sqldelight.runtime.rx.mapToList
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RecipeListViewModel @Inject constructor(private val database: Database): ViewModel()
{

    fun getRecipes(category: Category = Category.BREAKFAST): Observable<List<ViewRecipe>>
    {
        return database.recipeQueries.selectByCategory(category)
            .asObservable(Schedulers.io())
            .mapToList()
            .map { recipes -> recipes.map { ViewRecipe(it.name) } }
    }
}