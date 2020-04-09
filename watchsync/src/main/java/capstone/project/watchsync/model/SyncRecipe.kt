package capstone.project.watchsync.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SyncRecipe(val id: Long, val name: String)