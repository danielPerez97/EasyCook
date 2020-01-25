package capstone.project.easycook.viewmodel

import capstone.project.easycook.model.ViewRecipe

class RecipeListViewModel {

    fun getRecipes(): List<ViewRecipe>
    {
        return listOf(
            ViewRecipe("fjdklsafdsajk"),
            ViewRecipe("fjdklsafdsajk"),
            ViewRecipe("fjdklsfdsaajk"),
            ViewRecipe("fjdklsfdsafajk"),
            ViewRecipe("fjdklsajk"),
            ViewRecipe("fjdklsfdsaajk"),
            ViewRecipe("fjdklsfdsaajk"),
            ViewRecipe("fjdklsfdsafdsajk"),
            ViewRecipe("fjdklsfdsaajk"),
            ViewRecipe("fjdklsajfdsafk")
        )
    }
}