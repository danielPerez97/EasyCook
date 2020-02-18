package capstone.project.easycook.viewmodel;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import capstone.project.database.recipe.Database;

public class CookRecipeViewModel extends ViewModel
{
    private Database database;

    @Inject
    CookRecipeViewModel(Database database)
    {
        this.database = database;
    }
}
