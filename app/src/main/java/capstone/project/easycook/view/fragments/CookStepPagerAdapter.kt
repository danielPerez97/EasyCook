package capstone.project.easycook.view.fragments

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import capstone.project.easycook.model.ViewStep

class CookStepPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity)
{
    private var steps: List<ViewStep> = emptyList()

    override fun getItemCount(): Int = steps.size

    override fun createFragment(position: Int): Fragment = CookStepFragment(steps[position])

    fun setData(steps: List<ViewStep>)
    {
        this.steps = steps
        notifyDataSetChanged()
    }
}