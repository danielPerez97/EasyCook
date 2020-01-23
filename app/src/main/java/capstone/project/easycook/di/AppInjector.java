// Skeleton Injector that we define but Dagger will fill in for us
package capstone.project.easycook.di;

import capstone.project.easycook.MainActivity;

import javax.inject.Singleton;
import dagger.Component;

// This will be able to inject the string module defined in this directory
@Component(modules = StringModule.class)
@Singleton
public interface AppInjector
{
    public void inject(MainActivity activity);
}
