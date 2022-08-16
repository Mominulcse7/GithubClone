package com.mominulcse7.githubclone.utils

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.mominulcse7.githubclone.BuildConfig
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun Activity.closeKeyboard() {
    try {
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    } catch (e: Exception) {
    }
}

fun Context.haveNetwork(): Boolean {
    val connectivityManager = getSystemService(Activity.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    } else {
        @Suppress("DEPRECATION") val networkInfo =
            connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return networkInfo.isConnected
    }
}

fun getSqlToDDMMYYHHSS(inputDate: String?): String? {
    val dbFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    val newFormat = SimpleDateFormat("MM-dd-yy hh:mm a", Locale.US)
    var strDateTime = inputDate
    val date: Date?
    try {
        date = inputDate?.let { dbFormat.parse(it) }
        strDateTime = date?.let { newFormat.format(it) }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return strDateTime
}

fun Activity.setToolbarTitle(title: String) {
    try {
        (this as AppCompatActivity).supportActionBar!!.title = title
    } catch (e: Exception) {
    }
}

fun logPrint(any: Any?) {
    if (BuildConfig.DEBUG)
        Log.d("010101", "\n\nLogData = " + Gson().toJson(any) + "\n\n\n")
}
