package capstone.project.easycook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import daniel.perez.easycook.R;
import daniel.perez.easycook.databinding.ActivitySelectCategoryBinding;

public class SelectCategoryActivity extends AppCompatActivity
{

    ActivitySelectCategoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.breakfastIv.setOnClickListener(v ->
        {
            toast(v);
        });
        binding.lunchIv.setOnClickListener(v ->
        {
            toast(v);
        });

        binding.dinnerIv.setOnClickListener(v ->
        {
            toast(v);
        });

        binding.dessertsIv.setOnClickListener(v ->
        {
            toast(v);
        });

        binding.addRecipeBtn.setOnClickListener(v ->
        {
            toast(v);
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


}
