@file:JvmName("CategorySchema")

package capstone.project.database

import com.squareup.sqldelight.ColumnAdapter

    enum class Category
    {
        BREAKFAST, LUNCH, DINNER, DESSERT, UNKNOWN
    }


    @get:JvmName("categoryAdapter") val adapter = object : ColumnAdapter<Category, String>
    {
        override fun decode(databaseValue: String): Category {
            return when (databaseValue) {
                "Breakfast" -> Category.BREAKFAST
                "Lunch" -> Category.LUNCH
                "Dinner" -> Category.DINNER
                "Dessert" -> Category.DESSERT
                else -> Category.UNKNOWN
            }
        }

        override fun encode(value: Category): String {
            return when (value) {
                Category.BREAKFAST -> "Breakfast"
                Category.LUNCH -> "Lunch"
                Category.DINNER -> "Dinner"
                Category.DESSERT -> "Dessert"
                Category.UNKNOWN -> "Unknown"
            }
        }
    }