package capstone.project.easycook.view.createrecipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import capstone.project.easycook.Utils;
import capstone.project.easycook.di.viewmodel.ViewModelFactory;
import capstone.project.easycook.model.ViewIngredient;
import capstone.project.easycook.model.ViewStep;
import capstone.project.easycook.view.adapter.AddIngredientAdapter;
import capstone.project.easycook.view.adapter.AddStepAdapter;
import capstone.project.easycook.viewmodel.CreateRecipeViewModel;
import daniel.perez.easycook.databinding.ActivityCreateRecipeBinding;

public class CreateRecipeActivity extends AppCompatActivity
{

    @Inject ViewModelFactory factory;
    @Inject List<ViewIngredient> dummyIngredients;
    CreateRecipeViewModel viewModel;
    ActivityCreateRecipeBinding binding;
    AddIngredientAdapter ingredientAdapter;
    AddStepAdapter stepsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Utils.injector(this).inject(this);
        super.onCreate(savedInstanceState);
        binding = ActivityCreateRecipeBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this, factory).get(CreateRecipeViewModel.class);

        // Set up RecyclerView adapters
        ingredientAdapter = new AddIngredientAdapter();

        //
        binding.ingredientsList.setLayoutManager(new NoScroll(this));
        binding.ingredientsList.setAdapter(ingredientAdapter);
        binding.addIngredientBtn.setOnClickListener(v -> ingredientAdapter.addData(new ViewIngredient("", "")));

        ingredientAdapter.setData(dummyIngredients);

        // Finish the Activity
        binding.finishBtn.setOnClickListener(v -> save());
    }

    private void save()
    {
        List<ViewIngredient> ingredients = ingredientAdapter.ingredients();
//        List<ViewStep> steps = stepsAdapter.steps();

        // Notify the ViewModel
//        viewModel.save(
//                binding.editName.getText().toString(),
//                binding.descTv.getText().toString(),
//                ingredients,
//                steps)
    }

    private static class NoScroll extends LinearLayoutManager
    {

        public NoScroll(Context context) {
            super(context);
        }

        @Override
        public boolean canScrollVertically() {
            return false;
        }
    }
}
