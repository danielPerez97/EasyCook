package capstone.project.watch.view.stepslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import capstone.project.core.ViewStep
import capstone.project.easycook.databinding.RecipeStepEntryBinding

class StepViewFragment(private val step: ViewStep): Fragment()
{

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val binding = RecipeStepEntryBinding.inflate(inflater, container, false)
        binding.stepNumber.text = step.number.toString()
        binding.stepDescription.text = step.description
        return binding.root
    }
}