@file:JvmName("Utils")

package capstone.project.easycook

import androidx.appcompat.app.AppCompatActivity
import capstone.project.easycook.di.AppInjector

fun AppCompatActivity.injector(): AppInjector
{
    return (this.application as BaseApplication).injector()
}