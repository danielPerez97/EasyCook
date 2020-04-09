package capstone.project.watch.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class WatchModule(private val appContext: Context)
{
    @Provides
    fun provideContext(): Context = appContext
}