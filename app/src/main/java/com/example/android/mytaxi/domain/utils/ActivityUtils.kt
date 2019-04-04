package br.cericatto.mytaxitest.domain.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle

object ActivityUtils {
    fun startActivity(activity: Activity, clazz: Class<*>) {
        val intent = Intent(activity, clazz)
        activity.startActivity(intent)
        NavigationUtils.animate(activity, NavigationUtils.Animation.GO)
    }

    fun startActivityExtras(activity: Activity, clazz: Class<*>, key: String, value: Any) {
        val intent = Intent(activity, clazz)
        val extras = getExtra(Bundle(), key, value)
        intent.putExtras(extras)

        activity.startActivity(intent)
        NavigationUtils.animate(activity, NavigationUtils.Animation.GO)
    }

    private fun getExtra(extras: Bundle, key: String, value: Any): Bundle {
        when (value) {
            is String -> extras.putString(key, value)
            is Int -> extras.putInt(key, value)
            is Long -> extras.putLong(key, value)
            is Boolean -> extras.putBoolean(key, value)
        }
        return extras
    }
}