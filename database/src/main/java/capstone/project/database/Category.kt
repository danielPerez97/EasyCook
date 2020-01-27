package capstone.project.database

import com.squareup.sqldelight.ColumnAdapter

class Category
{
    enum class Categories
    {
        BREAKFAST, LUNCH, DINNER, DESSERT, UNKNOWN
    }

    companion object
    {
        @JvmStatic
        val adapter: ColumnAdapter<Categories, String> = object : ColumnAdapter<Categories, String>
        {
            override fun decode(databaseValue: String): Categories
            {
                return when(databaseValue)
                {
                    "Breakfast" -> Categories.BREAKFAST
                    "Lunch" -> Categories.LUNCH
                    "Dinner" -> Categories.DINNER
                    "Dessert" -> Categories.DESSERT
                    else -> Categories.UNKNOWN
                }
            }

            override fun encode(value: Categories): String
            {
                return when(value)
                {
                    Categories.BREAKFAST -> "Breakfast"
                    Categories.LUNCH -> "Lunch"
                    Categories.DINNER -> "Dinner"
                    Categories.DESSERT -> "Dessert"
                    Categories.UNKNOWN -> "Unknown"
                }
            }
        }
    }
}