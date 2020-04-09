@file:JvmName("Utils")

package capstone.project.watch

import android.support.wearable.activity.WearableActivity
import capstone.project.watch.di.WatchInjector

fun WearableActivity.injector(): WatchInjector
{
    return (this.application as BaseApplication).injector
}