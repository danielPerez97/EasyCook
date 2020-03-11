package capstone.project.easycookwatch.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class WatchModule(private val appContext: Context)
{
    @Provides
    fun provideContext(): Context = appContext
}