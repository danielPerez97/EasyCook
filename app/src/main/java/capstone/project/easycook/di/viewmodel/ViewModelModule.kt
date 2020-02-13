package capstone.project.easycook.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import capstone.project.easycook.viewmodel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule

{
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory?): ViewModelProvider.Factory?

    @Binds
    @IntoMap
    @ViewModelKey(CookRecipeViewModel::class)
    abstract fun cookViewModel(viewModel: CookRecipeViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(CreateRecipeViewModel::class)
    abstract fun createViewModel(viewModel: CreateRecipeViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(RecipeListViewModel::class)
    abstract fun recipeListViewModel(viewModel: RecipeListViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(RecipeViewModel::class)
    abstract fun recipeViewModel(viewModel: RecipeViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(SelectCategoryViewModel::class)
    abstract fun selectViewModel(viewModel: SelectCategoryViewModel?): ViewModel?
}