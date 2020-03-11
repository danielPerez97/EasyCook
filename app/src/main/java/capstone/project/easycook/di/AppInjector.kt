// Skeleton Injector that we define but Dagger will fill in for us
package capstone.project.easycook.di

import capstone.project.easycook.di.viewmodel.ViewModelModule
import capstone.project.easycook.view.*
import dagger.Component
import javax.inject.Singleton

// This will be able to inject the string module defined in this directory
@Singleton
@Component(modules = [AppModule::class, DatabaseModule::class, ViewModelModule::class])
interface AppInjector
{
    fun inject(activity: SelectCategoryActivity)
    fun inject(activity: RecipeListActivity)
    fun inject(activity: CookRecipeActivity)
    fun inject(activity: CreateRecipeActivity)
    fun inject(activity: RecipeActivity)

    fun inject(activity: DebugActivity)
}