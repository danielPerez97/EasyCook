package capstone.project.easycookwatch.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import capstone.project.easycookwatch.Utils;
import capstone.project.easycookwatch.di.viewmodel.ViewModelFactory;
import capstone.project.easycookwatch.viewmodel.RecipeViewModel;
import capstone.project.easycookwatch.databinding.ActivityRecipeBinding;

public class RecipeActivity extends AppCompatActivity
{
    @Inject ViewModelFactory factory;
    RecipeViewModel viewModel;
    ActivityRecipeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Utils.injector(this).inject(this);
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this, factory).get(RecipeViewModel.class);
    }
}
