package capstone.project.watch.di

import dagger.Component
import capstone.project.watch.view.MainActivity
import javax.inject.Singleton

@Component(modules = [WatchModule::class, SyncServiceModule::class])
@Singleton
interface WatchInjector
{
    fun inject(activity: MainActivity)
}