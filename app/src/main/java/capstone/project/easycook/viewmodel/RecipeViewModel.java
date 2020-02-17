package capstone.project.easycook.viewmodel;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import capstone.project.database.recipe.Database;
import capstone.project.easycook.model.ViewRecipe;

public class RecipeViewModel extends ViewModel
{
    private Database database;

    @Inject
    RecipeViewModel(Database database)
    {
        this.database = database;
    }
}
