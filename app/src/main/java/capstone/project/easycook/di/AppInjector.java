// Skeleton Injector that we define but Dagger will fill in for us
package capstone.project.easycook.di;

import javax.inject.Singleton;

import capstone.project.easycook.di.viewmodel.ViewModelModule;
import capstone.project.easycook.view.CookRecipeActivity;
import capstone.project.easycook.view.CreateRecipeActivity;
import capstone.project.easycook.view.RecipeActivity;
import capstone.project.easycook.view.RecipeListActivity;
import capstone.project.easycook.view.SelectCategoryActivity;
import dagger.Component;

// This will be able to inject the string module defined in this directory
@Component(modules = {AppModule.class, ViewModelModule.class})
@Singleton
public interface AppInjector
{
    void inject(SelectCategoryActivity activity);
    void inject(RecipeListActivity activity);
    void inject(CookRecipeActivity activity);
    void inject(CreateRecipeActivity activity);
    void inject(RecipeActivity activity);
}
