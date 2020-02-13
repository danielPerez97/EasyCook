package capstone.project.easycook.viewmodel

import androidx.lifecycle.ViewModel
import capstone.project.easycook.model.ViewRecipe
import javax.inject.Inject

class RecipeListViewModel @Inject constructor(private val dummyRecipes: List<ViewRecipe>): ViewModel()
{

    fun getRecipes(): List<ViewRecipe> = dummyRecipes
}