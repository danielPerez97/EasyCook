package capstone.project.watch.di

import android.content.Context
import capstone.project.watchsync.SyncService
import capstone.project.watchsync.SyncServiceImpl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SyncServiceModule
{
    @Provides
    @Singleton
    fun provideSyncService(context: Context, moshi: Moshi): SyncService
    {
        return SyncServiceImpl(context, moshi)
    }
}