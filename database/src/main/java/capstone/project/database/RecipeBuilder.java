package capstone.project.database;

import java.util.Objects;

import capstone.project.database.recipe.Database;

// Here's a helpful article on the Builder pattern to give you an idea. You shouldn't have to make
// a nested class like the example they use here, RecipeBuilder would be the inner Builder class they
// use in their example
// http://www.informit.com/articles/article.aspx?p=1216151&seqNum=2
public class RecipeBuilder // implements IRecipeBuilder
{
    Database database;

    RecipeBuilder(Database database)
    {
        // Use this method! It will cover you and provides a useful error.
        Objects.requireNonNull(database, "database == null");
        this.database = database;
    }
}
