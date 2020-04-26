package capstone.project.watch.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.wear.widget.WearableLinearLayoutManager
import capstone.project.core.ViewRecipe
import capstone.project.easycook.databinding.ActivityMainBinding
import capstone.project.watch.BaseApplication
import capstone.project.watch.view.adapter.RecipeListAdapter
import capstone.project.watch.view.stepslist.StepsListActivity
import capstone.project.watch.viewmodel.MainActivityViewModel
import capstone.project.watchsync.Request
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber
import javax.inject.Inject

class MainActivity : FragmentActivity(), Consumer<Request<List<ViewRecipe>>>
{
    private lateinit var binding: ActivityMainBinding
    private val recipeAdapter = RecipeListAdapter()
    private var nodeId: String? = null
    private val disposables = CompositeDisposable()
    private var state: Request<List<ViewRecipe>> = Request.CheckingForPhone
    @Inject lateinit var viewModel: MainActivityViewModel
    private var success = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        (application as BaseApplication).injector.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.recipeList)
        {
            adapter = recipeAdapter
            isEdgeItemsCenteringEnabled = true
            layoutManager = WearableLinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            isCircularScrollingGestureEnabled = true
            bezelFraction = 0.5f
        }

        // Enables Always-on
        handleRecyclerViewClicks()
        checkIfPhoneHasRecipes()
    }

    override fun onResume()
    {
        super.onResume()
        viewModel.onResume()
    }

    override fun onDestroy()
    {
        super.onDestroy()
        disposables.dispose()
    }

    private fun checkIfPhoneHasRecipes()
    {
        disposables += viewModel.recipes()
            .subscribe(this)
    }

    private fun handleRecyclerViewClicks()
    {
        disposables += recipeAdapter.clicks()
            .subscribe {
                if(nodeId != null)
                {
                    val intent = Intent(this, StepsListActivity::class.java)
                    intent.putExtra("RECIPE_ID", it.recipeId)
                    intent.putExtra("NODE_ID", nodeId)
                    startActivity(intent)
                }
            }
    }

    override fun accept(result: Request<List<ViewRecipe>>)
    {
        state = when(result)
        {
            is Request.CheckingForPhone ->
            {
                if(!success)
                {
                    Timber.i("Retrieving Phones")
                    binding.recipeList.visibility = View.GONE
                    binding.progressSpinner.visibility = View.VISIBLE
                }
                result
            }
            is Request.PhoneAvailable ->
            {
                val node = result.info.nodes.first()
                nodeId = node.id
                Timber.i("${node.displayName} Available")
                result
            }
            is Request.PhoneUnavailable ->
            {
                Timber.e(result.exception)
                result
            }
            Request.RequestInTransit ->
            {
                Timber.i("Request in Transit")
                result
            }
            is Request.Success<List<ViewRecipe>> ->
            {
                Timber.i("Successfully retrieved recipes")
                success = true
                recipeAdapter.setData( result.data )

                binding.progressSpinner.visibility = View.GONE
                binding.failureImage.visibility = View.GONE
                binding.recipeList.visibility = View.VISIBLE
                binding.recipeList.requestFocus()
                result
            }
            is Request.Failure ->
            {
                binding.recipeList.visibility = View.GONE
                binding.progressSpinner.visibility = View.GONE
                binding.failureImage.visibility = View.VISIBLE
                result
            }
        }

    }
}
