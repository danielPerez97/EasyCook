package capstone.network.client

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Recipe(
    val id: Long,
    val name: String,
    val category: String,
    val description: String,
    @Json(name = "time_estimate") val timeEstimate: String,
    @Json(name = "_main") val main: Boolean
)