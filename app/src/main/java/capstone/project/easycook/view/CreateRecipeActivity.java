package capstone.project.easycook.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import capstone.project.easycook.Utils;
import capstone.project.easycook.di.viewmodel.ViewModelFactory;
import capstone.project.easycook.viewmodel.CreateRecipeViewModel;
import daniel.perez.easycook.databinding.ActivityCreateRecipeBinding;

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
