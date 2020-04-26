package capstone.project.core.utils

import android.content.Context
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2

fun Context.toast(message: String, time: Int = Toast.LENGTH_SHORT)
{
    Toast.makeText(this, message, time).show()
}

fun ViewPager2.next(): Boolean
{
    currentItem += 1
    return true
}

fun ViewPager2.back(): Boolean
{
    currentItem -= 1
    return true
}