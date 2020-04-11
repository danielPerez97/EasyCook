package capstone.network.client;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RecipeService
{
    @GET("/")
    Observable<List<Recipe>> getAllRecipes();

    @GET("/{id}")
    Observable<Recipe> getRecipeId(@Path("id") long id);

    @POST("/")
    Observable<Recipe> postRecipe(@Body Recipe recipe);

    @DELETE("/{id}")
    Completable deleteRecipe(@Path("id") long id);

    @PUT("/")
    Observable<Recipe> updateRecipe(@Body Recipe recipe);

}
