package capstone.project.easycook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import daniel.perez.easycook.databinding.ActivityRecipeListBinding

class RecipeListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecipeListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var binding: ActivityRecipeListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeListBinding.inflate(this.layoutInflater)
        setContentView(binding.root)

        viewManager = LinearLayoutManager(this)
        viewAdapter = RecipeListAdapter()

        with(binding.recipeList)
        {

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        viewAdapter.setData(listOf(
            ViewRecipe("fjdklsafdsajk"),
            ViewRecipe("fjdklsafdsajk"),
            ViewRecipe("fjdklsfdsaajk"),
            ViewRecipe("fjdklsfdsafajk"),
            ViewRecipe("fjdklsajk"),
            ViewRecipe("fjdklsfdsaajk"),
            ViewRecipe("fjdklsfdsaajk"),
            ViewRecipe("fjdklsfdsafdsajk"),
            ViewRecipe("fjdklsfdsaajk"),
            ViewRecipe("fjdklsajfdsafk")
            ))
    }
}
