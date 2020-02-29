package capstone.project.easycook.model

data class ViewStep @JvmOverloads constructor(var number: Long, var description: String, val externalRecipe: Long? = null)