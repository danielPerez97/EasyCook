package capstone.project.watch.di

import dagger.Component
import capstone.project.watch.view.MainActivity

@Component(modules = [WatchModule::class])
interface WatchInjector
{
    fun inject(activity: MainActivity)
}