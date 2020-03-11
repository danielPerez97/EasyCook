package capstone.project.easycookwatch

import android.app.Application
import capstone.project.easycookwatch.di.DaggerWatchInjector
import capstone.project.easycookwatch.di.WatchInjector
import capstone.project.easycookwatch.di.WatchModule

class BaseApplication: Application()
{
    lateinit var injector: WatchInjector
        private set

    override fun onCreate()
    {
        super.onCreate()

        injector = DaggerWatchInjector.builder()
            .watchModule(WatchModule(this))
            .build()
    }
}