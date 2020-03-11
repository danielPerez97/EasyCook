package capstone.project.easycook.viewmodel;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import capstone.project.database.recipe.Database;

public class RecipeViewModel extends ViewModel
{
    private Database database;

    @Inject
    RecipeViewModel(Database database)
    {
        this.database = database;
    }
}
