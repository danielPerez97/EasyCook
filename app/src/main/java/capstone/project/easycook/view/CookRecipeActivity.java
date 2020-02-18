package capstone.project.easycook.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import capstone.project.easycook.Utils;
import capstone.project.easycook.di.viewmodel.ViewModelFactory;
import capstone.project.easycook.viewmodel.CookRecipeViewModel;
import daniel.perez.easycook.databinding.ActivityCookRecipeBinding;

public class CookRecipeActivity extends AppCompatActivity
{

    @Inject ViewModelFactory factory;
    CookRecipeViewModel viewModel;
    ActivityCookRecipeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Utils.injector(this).inject(this);
        super.onCreate(savedInstanceState);
        binding = ActivityCookRecipeBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this, factory).get(CookRecipeViewModel.class);
    }
}
