package capstone.project.easycook.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import javax.inject.Inject;

import capstone.project.database.recipe.Database;
import capstone.project.easycook.Utils;
import daniel.perez.easycook.R;

public class DebugActivity extends AppCompatActivity
{

    @Inject Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Utils.injector(this).inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
    }
}
