package capstone.project.easycookwatch.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import capstone.project.easycookwatch.Utils;
import capstone.project.easycookwatch.di.viewmodel.ViewModelFactory;
import capstone.project.easycookwatch.viewmodel.CreateRecipeViewModel;
import capstone.project.easycookwatch.databinding.ActivityCreateRecipeBinding;

public class CreateRecipeActivity extends AppCompatActivity
{

    @Inject ViewModelFactory factory;
    CreateRecipeViewModel viewModel;
    ActivityCreateRecipeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Utils.injector(this).inject(this);
        super.onCreate(savedInstanceState);
        binding = ActivityCreateRecipeBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this, factory).get(CreateRecipeViewModel.class);
    }
}
