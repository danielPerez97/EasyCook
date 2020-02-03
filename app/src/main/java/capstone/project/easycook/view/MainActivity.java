package capstone.project.easycook.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import capstone.project.easycook.BaseApplication;

public class MainActivity extends AppCompatActivity
{
    // Specify what to inject
    @Inject String message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ((BaseApplication)getApplication()).injector().inject(this);
    }
}
