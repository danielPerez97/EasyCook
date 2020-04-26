package capstone.project.core

data class ViewRecipe @JvmOverloads constructor(val recipeId: Long,
                      val recipeName: String,
                      val recipeDescription: String = "")