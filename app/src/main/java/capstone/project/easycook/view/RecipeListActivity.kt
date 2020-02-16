package capstone.project.easycook.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import capstone.project.easycook.di.viewmodel.ViewModelFactory
import capstone.project.easycook.injector
import capstone.project.easycook.view.adapter.RecipeListAdapter
import capstone.project.easycook.viewmodel.RecipeListViewModel
import daniel.perez.easycook.databinding.ActivityRecipeListBinding
import javax.inject.Inject

class RecipeListActivity : AppCompatActivity()
{
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecipeListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var binding: ActivityRecipeListBinding
    private lateinit var viewModel: RecipeListViewModel
    @Inject lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?)
    {
        injector().inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeListBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, factory).get(RecipeListViewModel::class.java)

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
