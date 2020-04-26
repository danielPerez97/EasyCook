package capstone.project.watch.di

import android.content.Context
import capstone.project.watch.viewmodel.MainActivityViewModel
import capstone.project.watch.sync.SyncService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WatchModule(private val appContext: Context)
{
    @Provides
    @Singleton
    fun provideContext(): Context = appContext

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideMainViewModel(syncService: SyncService): MainActivityViewModel = MainActivityViewModel(syncService)


}