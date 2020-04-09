package capstone.project.watchsync.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SyncStepRequest(val recipeId: Long)