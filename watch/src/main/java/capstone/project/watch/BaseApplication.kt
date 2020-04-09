package capstone.project.watch

import android.app.Application
import capstone.project.watch.di.DaggerWatchInjector
import capstone.project.watch.di.WatchInjector
import capstone.project.watch.di.WatchModule

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