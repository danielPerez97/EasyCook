package capstone.project.easycookwatch.di

import dagger.Component
import capstone.project.easycookwatch.view.MainActivity

@Component(modules = [WatchModule::class])
interface WatchInjector
{
    fun inject(activity: MainActivity)
}