package capstone.project.watch.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import capstone.project.core.ViewStep
import capstone.project.watch.view.stepslist.StepViewFragment

class StepPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa)
{
    private var steps: List<ViewStep> = emptyList()

    override fun getItemCount(): Int = steps.size

    override fun createFragment(position: Int): Fragment = StepViewFragment(steps[position])

    fun setData(newSteps: List<ViewStep>)
    {
        steps = newSteps
        notifyDataSetChanged()
    }

}