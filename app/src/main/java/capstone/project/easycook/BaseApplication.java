package capstone.project.easycook;

import android.app.Application;

import capstone.project.easycook.di.AppInjector;
import capstone.project.easycook.di.DaggerAppInjector;

public class BaseApplication extends Application
{
    private AppInjector injector;

    @Override
    public void onCreate()
    {
        super.onCreate();
        injector = DaggerAppInjector.create();
    }

    public AppInjector injector()
    {
        return injector;
    }
}
