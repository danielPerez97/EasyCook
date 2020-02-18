package capstone.project.database

internal data class Step @JvmOverloads constructor(val recipeId: Long? = null, val number: Long, val desc: String, var externalRecipeId: Long? = null)
{
    constructor(number: Long, desc: String, externalRecipeId: Long?): this(null, number, desc, externalRecipeId)
}