package capstone.project.database;

import capstone.project.database.recipe.Recipe;

public interface IRecipeBuilder
{
    public RecipeBuilder name(String name);

    public RecipeBuilder description(String description);

    public RecipeBuilder category(Category category);

    public RecipeBuilder addStep(Long stepNumber, String stepDescription);

    public RecipeBuilder addStep(Long stepNumber, String stepDescription, Long optionalRecipeId);

    public Recipe insertOrUpdate();

    // MAKE THIS INSIDE RECIPE BUILDER
    // This method will construct a RecipeBuilder from an existing recipe
//    public static RecipeBuilder fromRecipe(Database database, Recipe recipe);
}
