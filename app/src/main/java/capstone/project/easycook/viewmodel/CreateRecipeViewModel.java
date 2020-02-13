package capstone.project.easycook.viewmodel;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import capstone.project.easycook.model.ViewRecipe;

public class CreateRecipeViewModel extends ViewModel
{

    @Inject
    CreateRecipeViewModel(List<ViewRecipe> recipes)
    {

    }
}
