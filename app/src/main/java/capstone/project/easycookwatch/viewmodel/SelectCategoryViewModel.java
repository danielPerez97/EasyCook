package capstone.project.easycookwatch.viewmodel;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import capstone.project.database.recipe.Database;

public class SelectCategoryViewModel extends ViewModel
{

    private Database database;

    @Inject
    SelectCategoryViewModel(Database database)
    {
        this.database = database;
    }
}
