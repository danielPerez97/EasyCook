// Skeleton Injector that we define but Dagger will fill in for us
package capstone.project.easycook.di;

import javax.inject.Singleton;

import capstone.project.easycook.di.viewmodel.ViewModelModule;
import capstone.project.easycook.view.RecipeListActivity;
import capstone.project.easycook.view.SelectCategoryActivity;
import dagger.Component;

// This will be able to inject the string module defined in this directory
@Component(modules = {AppModule.class, ViewModelModule.class})
@Singleton
public interface AppInjector
{
    public void inject(SelectCategoryActivity activity);
    public void inject(RecipeListActivity activity);
}
