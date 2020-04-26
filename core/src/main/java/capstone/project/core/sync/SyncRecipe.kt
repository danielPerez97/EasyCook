package capstone.project.core.sync

import com.squareup.moshi.JsonClass

/**
 * This class is used instead of the database Recipe type because it allows us to use Moshi to
 * serialize it through JSON, where the database Recipe type is generated code
 */

@JsonClass(generateAdapter = true)
data class SyncRecipe(val id: Long, val name: String)