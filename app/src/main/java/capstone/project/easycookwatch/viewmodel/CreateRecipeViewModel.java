package capstone.project.easycookwatch.viewmodel;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import capstone.project.database.recipe.Database;

public class CreateRecipeViewModel extends ViewModel
{

    private Database database;

    @Inject
    CreateRecipeViewModel(Database database)
    {
        this.database = database;
    }
}
