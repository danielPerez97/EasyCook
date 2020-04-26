package capstone.project.watch.view.stepslist

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.wear.ambient.AmbientModeSupport
import capstone.project.core.ViewStep
import capstone.project.core.sync.SyncStepRequest
import capstone.project.core.utils.back
import capstone.project.core.utils.next
import capstone.project.easycook.databinding.ActivityStepsListBinding
import capstone.project.watch.BaseApplication
import capstone.project.watch.view.adapter.StepPagerAdapter
import capstone.project.watch.viewmodel.StepsListViewModel
import capstone.project.watchsync.Request
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber
import javax.inject.Inject

class StepsListActivity : FragmentActivity(), Consumer<Request<List<ViewStep>>>
{

    @Inject
    lateinit var viewModel: StepsListViewModel
    private lateinit var binding: ActivityStepsListBinding
    private val disposables = CompositeDisposable()
    private lateinit var ambientController: AmbientModeSupport.AmbientController
    private lateinit var stepsAdapter: StepPagerAdapter
    private var state: Request<List<ViewStep>> = Request.CheckingForPhone

    override fun onCreate(savedInstanceState: Bundle?)
    {
        (application as BaseApplication).injector.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityStepsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ambientController = AmbientModeSupport.attach(this)

        val nodeId = intent.getStringExtra("NODE_ID")!!
        val recipeId = intent.getLongExtra("RECIPE_ID", -1)
        stepsAdapter = StepPagerAdapter(this)
        binding.stepsPager.adapter = stepsAdapter


        disposables += viewModel.steps(nodeId, SyncStepRequest(recipeId))
            .subscribe(this)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean
    {
        return if (event.repeatCount == 0)
        {
            viewPagerButton(keyCode, event)
        } else
        {
            super.onKeyDown(keyCode, event)
        }
    }

    override fun accept(result: Request<List<ViewStep>>)
    {
        state = when (result)
        {
            Request.CheckingForPhone ->
            {
                Timber.i("Checking for phone")
                binding.progressSpinner.visibility = View.VISIBLE
                binding.stepsPager.visibility = View.GONE
                result
            }
            is Request.PhoneAvailable ->
            {
                val node = result.info.nodes.first()
//                nodeId = node.id
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
            is Request.Success ->
            {
                Timber.i("Found Steps Successfully")
                stepsAdapter.setData(result.data)

                binding.progressSpinner.visibility = View.GONE
                binding.failureImage.visibility = View.GONE
                binding.stepsPager.visibility = View.VISIBLE
                result
            }
            is Request.Failure ->
            {
                Timber.e(result.throwable)

                binding.progressSpinner.visibility = View.GONE
                binding.stepsPager.visibility = View.GONE
                binding.failureImage.visibility = View.VISIBLE
                result
            }
        }
    }

    private fun viewPagerButton(keyCode: Int, event: KeyEvent): Boolean
    {
        return when (keyCode)
        {
            KeyEvent.KEYCODE_STEM_1 -> binding.stepsPager.next()
            KeyEvent.KEYCODE_STEM_2 ->
            {
                if (binding.stepsPager.currentItem == 0)
                {
                    finish()
                    false
                }
                else
                {
                    binding.stepsPager.back()
                }
            }
            else -> super.onKeyDown(keyCode, event)
        }
    }
}
