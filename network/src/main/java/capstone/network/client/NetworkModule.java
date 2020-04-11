package capstone.network.client;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class NetworkModule
{

    @Provides
    @Singleton
    public OkHttpClient provideOkHttp()
    {
        return new OkHttpClient.Builder().build();
    }

    @Provides
    @Singleton
    public String provideBaseUrl()
    {
        return " http://easycook-server.herokuapp.com\n";
    }

    @Provides
    @Singleton
    Moshi provideMoshi()
    {
        Moshi moshi = new Moshi.Builder().build();
        Type type =  Types.newParameterizedType(List.class, Recipe.class);
        JsonAdapter<List<Recipe>> adapter = moshi.adapter(type);
        return moshi.newBuilder().add(type, adapter.lenient()).build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client, String baseUrl, Moshi moshi)
    {
        return new Retrofit.Builder()
                .client( client )
                .baseUrl( baseUrl )
                .addCallAdapterFactory( RxJava2CallAdapterFactory.createWithScheduler( Schedulers.io() ) )
                .addConverterFactory( MoshiConverterFactory.create(moshi) )
                .build();
    }

    @Provides
    @Singleton
    public RecipeService provideService( Retrofit retrofit )
    {
        return retrofit.create( RecipeService.class );
    }
}
