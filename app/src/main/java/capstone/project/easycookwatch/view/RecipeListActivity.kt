package capstone.project.easycookwatch.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import capstone.project.database.Category
import capstone.project.easycookwatch.di.viewmodel.ViewModelFactory
import capstone.project.easycookwatch.injector
import capstone.project.easycookwatch.view.adapter.RecipeListAdapter
import capstone.project.easycookwatch.viewmodel.RecipeListViewModel
import capstone.project.easycookwatch.databinding.ActivityRecipeListBinding
import io.reactivex.android.schedulers.AndroidSchedulers
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
        setSupportActionBar(binding.toolbar)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, factory).get(RecipeListViewModel::class.java)

        viewManager = LinearLayoutManager(this)
        viewAdapter = RecipeListAdapter()

        Toast.makeText(applicationContext, intent.getStringExtra("CATEGORY"), Toast.LENGTH_SHORT).show()

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

        val category = Category.valueOf(intent.getStringExtra("CATEGORY")!!)

        val disposable = viewModel.getRecipes(category)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                viewAdapter.setData(it)
            }
        //viewAdapter.setData(viewModel.getRecipes())
    }
}
