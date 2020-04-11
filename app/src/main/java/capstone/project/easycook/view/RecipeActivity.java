package capstone.project.easycook.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import javax.inject.Inject;

import capstone.project.database.recipe.Ingredient;
import capstone.project.easycook.Utils;
import capstone.project.easycook.databinding.ActivityRecipeBinding;
import capstone.project.easycook.di.viewmodel.ViewModelFactory;
import capstone.project.easycook.view.adapter.IngredientsListAdapter;
import capstone.project.easycook.view.adapter.RecipeListAdapter;
import capstone.project.easycook.viewmodel.RecipeViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;

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
        long recipeId = getIntent().getLongExtra("RECIPE_ID", -1);

        binding.toCookRecipeBtn.setOnClickListener(v -> cookRecipe(recipeId));

        IngredientsListAdapter recipeListAdapter = new IngredientsListAdapter();
        binding.ingredientsView.setAdapter(recipeListAdapter);

        binding.ingredientsView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getIngredients(recipeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ingredients -> recipeListAdapter.setData(ingredients));
    }

    private void cookRecipe(long id)
    {
        Intent intent = new Intent(this, CookRecipeActivity.class);
        intent.putExtra("RECIPE_ID", id);
        startActivity(intent);
    }
}
