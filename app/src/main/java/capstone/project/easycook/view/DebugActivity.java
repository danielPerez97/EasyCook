package capstone.project.easycook.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import capstone.project.database.recipe.Database;
import capstone.project.easycook.Utils;
import daniel.perez.easycook.databinding.ActivityDebugBinding;

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
    }
}
