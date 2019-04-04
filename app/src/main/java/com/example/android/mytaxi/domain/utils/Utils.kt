package br.cericatto.mytaxitest.domain.utils

import android.content.Context
import android.net.ConnectivityManager

object Utils {
    fun checkConnection(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}