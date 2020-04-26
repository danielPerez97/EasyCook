package capstone.project.core.sync

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SyncStepRequest(val recipeId: Long)