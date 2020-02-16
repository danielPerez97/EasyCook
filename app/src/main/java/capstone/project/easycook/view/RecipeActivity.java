package capstone.project.easycook.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;

import capstone.project.easycook.Utils;
import capstone.project.easycook.di.viewmodel.ViewModelFactory;
import capstone.project.easycook.model.ViewRecipe;
import capstone.project.easycook.viewmodel.RecipeViewModel;
import daniel.perez.easycook.R;

public class RecipeActivity extends AppCompatActivity
{
    @Inject ViewModelFactory factory;
    RecipeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Utils.injector(this).inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        viewModel = new ViewModelProvider(this, factory).get(RecipeViewModel.class);
    }
}
