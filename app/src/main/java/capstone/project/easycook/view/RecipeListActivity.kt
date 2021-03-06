package capstone.project.easycook.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import capstone.project.database.Category
import capstone.project.database.adapter
import capstone.project.easycook.databinding.ActivityRecipeListBinding
import capstone.project.easycook.di.viewmodel.ViewModelFactory
import capstone.project.easycook.injector
import capstone.project.easycook.view.adapter.RecipeListAdapter
import capstone.project.easycook.viewmodel.RecipeListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class RecipeListActivity : AppCompatActivity()
{
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecipeListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var binding: ActivityRecipeListBinding
    private lateinit var viewModel: RecipeListViewModel
    private val disposables = CompositeDisposable()
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

        disposables.add(viewModel.getRecipes(category)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                viewAdapter.setData(it)
            })

        disposables.add(viewAdapter.clicks()
            .subscribe{
                  startRecipeActivity(it.recipeId)
            })
    }

    private fun startRecipeActivity(id: Long)
    {
        val intent = Intent(this, RecipeActivity::class.java)
        intent.putExtra("RECIPE_ID", id)
        startActivity(intent)
    }
}
