package capstone.network.client;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

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
        return "https://www.google.com";
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit( OkHttpClient client, String baseUrl )
    {
        return new Retrofit.Builder()
                .client( client )
                .baseUrl( baseUrl )
                .addCallAdapterFactory( RxJava2CallAdapterFactory.createWithScheduler( Schedulers.io() ) )
                .build();
    }

    @Provides
    @Singleton
    public RecipeService provideService( Retrofit retrofit )
    {
        return retrofit.create( RecipeService.class );
    }
}
