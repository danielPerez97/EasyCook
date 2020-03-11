package capstone.project.easycook.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import capstone.project.easycook.databinding.CookStepEntryBinding
import capstone.project.easycook.model.ViewStep

class CookStepFragment(val step: ViewStep): Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        val binding = CookStepEntryBinding.inflate(inflater, container, false)
        binding.stepNumberTv.text = step.number.toString()
        binding.stepDescTv.text = step.description
        return binding.root
    }
}