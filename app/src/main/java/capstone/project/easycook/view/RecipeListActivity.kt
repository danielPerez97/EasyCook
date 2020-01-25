package capstone.project.easycook.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import capstone.project.easycook.view.adapter.RecipeListAdapter
import capstone.project.easycook.model.ViewRecipe
import capstone.project.easycook.viewmodel.RecipeListViewModel
import daniel.perez.easycook.databinding.ActivityRecipeListBinding

class RecipeListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecipeListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var binding: ActivityRecipeListBinding
    private lateinit var viewModel: RecipeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeListBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
//        viewModel = ViewModelProviders.of

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

        viewAdapter.setData(viewModel.getRecipes())
    }
}
