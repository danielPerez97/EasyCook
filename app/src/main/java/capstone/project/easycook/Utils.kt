@file:JvmName("Utils")

package capstone.project.easycook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import capstone.project.database.recipe.Step
import capstone.project.easycook.BaseApplication
import capstone.project.easycook.di.AppInjector
import capstone.project.easycook.model.ViewStep

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

fun List<Step>.toViewSteps(): List<ViewStep>
{
    return this.map { ViewStep(it.step_number, it.description) }
}