package capstone.project.watch.view

import android.content.Context
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import androidx.wear.widget.WearableLinearLayoutManager
import capstone.project.core.ViewRecipe
import capstone.project.watch.databinding.ActivityMainBinding
import capstone.project.watch.injector
import capstone.project.watch.view.adapter.RecipeListAdapter
import javax.inject.Inject

class MainActivity : WearableActivity()
{

    @Inject lateinit var context: Context
    private lateinit var binding: ActivityMainBinding
    private val recipeAdapter = RecipeListAdapter()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        injector().inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView()
        recipeAdapter.setData(listOf(ViewRecipe(-1, "Cereal"), ViewRecipe(-1, "Eggs")))

        // Enables Always-on
        setAmbientEnabled()
    }

    private fun recyclerView()
    {
        with(binding.recipesList)
        {
            adapter = recipeAdapter
            setHasFixedSize(true)
            isEdgeItemsCenteringEnabled = true
            layoutManager = WearableLinearLayoutManager(context)
            isCircularScrollingGestureEnabled = true
            bezelFraction = 0.5f
            requestFocus()
        }
    }
}
