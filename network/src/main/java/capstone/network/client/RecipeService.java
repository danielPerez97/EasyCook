package capstone.network.client;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RecipeService
{
    @GET("/")
    List<Recipe> getAllRecipes();
}
