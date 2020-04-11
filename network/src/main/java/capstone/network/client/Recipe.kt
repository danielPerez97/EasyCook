package capstone.network.client

data class Recipe(
    val id: Long,
    val name: String,
    val category: String,
    val description: String,
    val timeEstimate: String,
    val main: Boolean
)