package capstone.project.easycook.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import capstone.project.database.Category;
import capstone.project.database.RecipeBuilder;
import capstone.project.database.recipe.Database;
import capstone.project.database.recipe.Recipe;
import capstone.project.easycook.Utils;
import capstone.project.easycook.databinding.ActivityDebugBinding;

public class DebugActivity extends AppCompatActivity
{

    @Inject Database database;
    ActivityDebugBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Utils.injector(this).inject(this);
        super.onCreate(savedInstanceState);
        binding = ActivityDebugBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(v -> addDummyRecipes());
    }

    void addDummyRecipes()
    {
        // Sub-recipe for the wing sauce
        Recipe wingSauce = new RecipeBuilder(database)
                .name("Wing Sauce")
                .description("Will rek your colon lmao")
                .category(Category.LUNCH)
                .addStep(1L, "Add water to bowl")
                .addStep(2L, "Dump the canned sauce solution in bowl")
                .addStep(3L, "Mix")
                .main(false)
                .insertOrUpdate();

        Recipe cereal = new RecipeBuilder(database)
                .name("Corn Flakes")
                .description("Most important meal of the day!!! :)")
                .category(Category.BREAKFAST)
                .addStep(1L, "get bowl")
                .addStep(2L, "pour milk first")
                .addStep(3L, "pour cereal")
                .addStep(4L, "eat ;-)")
                .addIngredient("Milk", "2 cups")
                .addIngredient("Any healthy cereal", "42 g")
                .main(true)
                .insertOrUpdate();

        Recipe wings = new RecipeBuilder(database)
                .name("Chicken Wings")
                .description("Will give you diabeetus")
                .category(Category.LUNCH)
                .addStep(1L, "Unfreeze the meat")
                .addStep(2L, "Grill the wings for 30 min")
                .addStep(3L, "Make the sauce", wingSauce.get_id())
                .addStep(4L, "Coat the wings in the sauce")
                .addIngredient("Raw chicken wings", "69.420 kg")
                .addIngredient("Wing seasoning of your choice", "2 tbsp")
                .main(true)
                .insertOrUpdate();

        Recipe cookies = new RecipeBuilder(database)
                .name("Cookies")
                .description("Too much sugah!!!")
                .category(Category.DESSERT)
                .addStep(1L, "make stuff lol")
                .main(true)
                .insertOrUpdate();
    }
}
