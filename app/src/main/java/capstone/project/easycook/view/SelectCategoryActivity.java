package capstone.project.easycook.view;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.MaterialToolbar;
import com.jakewharton.rxbinding3.appcompat.RxToolbar;

import java.util.List;

import javax.inject.Inject;

import capstone.project.easycook.BaseApplication;
import capstone.project.easycook.Utils;
import capstone.project.easycook.di.viewmodel.ViewModelFactory;
import capstone.project.easycook.model.ViewRecipe;
import capstone.project.easycook.viewmodel.SelectCategoryViewModel;
import daniel.perez.easycook.R;
import daniel.perez.easycook.databinding.ActivitySelectCategoryBinding;
import io.reactivex.disposables.Disposable;

public class SelectCategoryActivity extends AppCompatActivity
{

    @Inject ViewModelFactory factory;
    SelectCategoryViewModel viewModel;
    ActivitySelectCategoryBinding binding;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Utils.injector(this).inject(this);
        super.onCreate(savedInstanceState);
        binding = ActivitySelectCategoryBinding.inflate(getLayoutInflater());
        toolbar = binding.getRoot().findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(binding.getRoot());
        if(factory == null)
        {
            Log.i("NULL", "Factory is null");
        }
        viewModel = new ViewModelProvider(this, factory).get(SelectCategoryViewModel.class);

        binding.breakfastIv.setOnClickListener(v ->
        {
            toast(v);
            startRecipeList();
        });
        binding.lunchIv.setOnClickListener(v ->
        {
            toast(v);
            startRecipeList();
        });

        binding.dinnerIv.setOnClickListener(v ->
        {
            toast(v);
            startRecipeList();
        });

        binding.dessertsIv.setOnClickListener(v ->
        {
            toast(v);
            startRecipeList();
        });

        binding.addRecipeBtn.setOnClickListener(v ->
        {
            toast(v);
            startRecipeList();
        });

        Disposable disposable = RxToolbar.itemClicks(toolbar)
                .filter(it -> it.getItemId() == R.id.action_debug)
                .subscribe( (MenuItem item) -> {
                    Intent intent = new Intent(this, DebugActivity.class);
                    startActivity(intent);
                });
    }

    private void toast(View view)
    {
        switch(view.getId())
        {
            case R.id.breakfast_iv:
            {
                Toast.makeText(this, "Breakfast Button", Toast.LENGTH_SHORT).show();
            }
            break;

            case R.id.lunch_iv:
            {
                Toast.makeText(this, "Lunch Button", Toast.LENGTH_SHORT).show();
            }
            break;

            case R.id.dinner_iv:
            {
                Toast.makeText(this, "Dinner Button", Toast.LENGTH_SHORT).show();
            }
            break;

            case R.id.desserts_iv:
            {
                Toast.makeText(this, "Dessert Button", Toast.LENGTH_SHORT).show();
            }
            break;

            case R.id.add_recipe_btn:
            {
                Toast.makeText(this, "New Recipe", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    private void startRecipeList()
    {
        Intent intent = new Intent(this, RecipeListActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.select_category_menu, menu);
        return true;
    }
}
