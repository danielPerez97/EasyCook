@file:JvmName("Utils")

package capstone.project.easycook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import capstone.project.easycook.di.AppInjector

fun AppCompatActivity.injector(): AppInjector
{
    return (this.application as BaseApplication).injector()
}

fun <A: AppCompatActivity, B: AppCompatActivity> A.startView(destination: Class<in B>, modifications: (Intent) -> Intent)
{
    var intent = Intent(this, destination)
    intent = modifications.invoke(intent)
    this.startActivity(intent)
}

fun <A: AppCompatActivity, B: AppCompatActivity> A.startView(destination: Class<B>)
{
    val intent = Intent(this, destination::class.java)
    this.startActivity(intent)
}