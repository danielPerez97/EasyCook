package capstone.project.core.sync

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SyncStep(val stepNumber: Long, val description: String)