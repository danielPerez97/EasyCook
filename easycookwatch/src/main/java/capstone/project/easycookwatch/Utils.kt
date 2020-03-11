@file:JvmName("Utils")

package capstone.project.easycookwatch

import android.support.wearable.activity.WearableActivity
import capstone.project.easycookwatch.di.WatchInjector

fun WearableActivity.injector(): WatchInjector
{
    return (this.application as BaseApplication).injector
}