package capstone.project.watch

import android.app.Application
import capstone.project.easycook.BuildConfig
import capstone.project.watch.di.DaggerWatchInjector
import capstone.project.watch.di.WatchInjector
import capstone.project.watch.di.WatchModule
import timber.log.Timber

class BaseApplication: Application()
{
    lateinit var injector: WatchInjector
        private set

    override fun onCreate()
    {
        super.onCreate()

        if(BuildConfig.DEBUG)
        {
            Timber.plant( Timber.DebugTree() )
        }

        injector = DaggerWatchInjector.builder()
            .watchModule(WatchModule(this))
            .build()
    }
}