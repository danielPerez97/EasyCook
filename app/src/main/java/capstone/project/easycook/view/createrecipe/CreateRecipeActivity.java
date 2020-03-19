package capstone.project.easycook.view.createrecipe;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import javax.inject.Inject;

import capstone.project.database.Category;
import capstone.project.easycook.Utils;
import capstone.project.easycook.di.viewmodel.ViewModelFactory;
import capstone.project.easycook.model.ViewIngredient;
import capstone.project.easycook.model.ViewStep;
import capstone.project.easycook.view.adapter.SimpleItemTouchHelperCallback;
import capstone.project.easycook.view.createrecipe.adapter.AddIngredientAdapter;
import capstone.project.easycook.view.createrecipe.adapter.AddStepAdapter;
import capstone.project.easycook.viewmodel.CreateRecipeViewModel;
import daniel.perez.easycook.databinding.ActivityCreateRecipeBinding;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class CreateRecipeActivity extends AppCompatActivity
{

    @Inject ViewModelFactory factory;
    CreateRecipeViewModel viewModel;
    ActivityCreateRecipeBinding binding;
    AddIngredientAdapter ingredientAdapter = new AddIngredientAdapter();
    AddStepAdapter stepsAdapter = new AddStepAdapter();
    CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Utils.injector(this).inject(this);
        super.onCreate(savedInstanceState);
        binding = ActivityCreateRecipeBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this, factory).get(CreateRecipeViewModel.class);

        // Set up the ingredients RecyclerView adapter
        binding.ingredientsList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.ingredientsList.setAdapter(ingredientAdapter);

        // Set up the steps RecyclerView adapter
        binding.stepsList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.stepsList.setAdapter(stepsAdapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(stepsAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(binding.stepsList);

        // Set up the buttons
        binding.addIngredientBtn.setOnClickListener(v -> ingredientAdapter.addData(new ViewIngredient("", "")));
        binding.addStepBtn.setOnClickListener(v -> stepsAdapter.addData(new ViewStep(-1, "")));

        // Finish the Activity
        binding.finishBtn.setOnClickListener(v -> save());
    }

    private void save()
    {
        // TODO gather the list of steps
        List<ViewIngredient> ingredients = ingredientAdapter.ingredients();
        List<ViewStep> steps = stepsAdapter.steps();

        // Notify the ViewModel
        disposables.add(viewModel.save(
                binding.editName.getText().toString(),
                binding.descTv.getText().toString(),
                category(),
                ingredients,
                steps)
        .observeOn( AndroidSchedulers.mainThread() )
        .subscribe(recipe -> finish()));
    }

    private Category category()
    {
        return Category.DINNER;
    }
}
