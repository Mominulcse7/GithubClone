package com.mominulcse7.githubclone.utils

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.mominulcse7.githubclone.BuildConfig
import com.mominulcse7.githubclone.R
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

fun Activity.showKeyboard() {
    try {
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(window.decorView, InputMethodManager.SHOW_IMPLICIT)
    } catch (e: Exception) {
    }
}

fun Activity.keyboardIsVisible(): Boolean {
    val isOpen = try {
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.isAcceptingText
    } catch (e: Exception) {
        false
    }
    return isOpen
}

fun Activity.haveNetwork(): Boolean {
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

fun Activity.popupAlert(title: String, msg: String) {
    MaterialAlertDialogBuilder(this).setCancelable(false)
        .setIcon(R.drawable.ic_alert)
        .setTitle(title)
        .setMessage(msg)
        .setPositiveButton(resources.getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
        }.show()
}

fun Activity.popupInfo(title: String, msg: String) {
    MaterialAlertDialogBuilder(this).setCancelable(false)
        .setIcon(R.drawable.ic_info)
        .setTitle(title)
        .setMessage(msg)
        .setPositiveButton(resources.getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
        }.show()
}

fun Context.popupAlertContext(title: String, msg: String) {
    MaterialAlertDialogBuilder(this).setCancelable(false)
        .setIcon(R.drawable.ic_alert)
        .setTitle(title)
        .setMessage(msg)
        .setPositiveButton(resources.getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
        }.show()
}

fun Context.toastShow(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Activity.popupNoInternet() {
    MaterialAlertDialogBuilder(this).setCancelable(false)
        .setIcon(R.drawable.ic_no_internet)
        .setTitle(resources.getString(R.string.no_internet))
        .setMessage(resources.getString(R.string.turn_on_internet))
        .setPositiveButton(resources.getString(R.string.open_settings)) { dialog, _ ->
            dialog.dismiss()
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }.setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }.show()
}

fun getTwoDigitDecimal(stNumber: Any?): String {
    if (stNumber == null) return "0.0"
    val number = try {
        stNumber.toString().toDouble()
    } catch (e: Exception) {
        0.0
    }
    return String.format("%.2f", number)
}

fun getCurrentSqlTime(): String {
    val calendar = Calendar.getInstance()
    val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    return currentDate.format(calendar.time)
}

fun getSqlToDDMMYYHHSS(inputDate: String?): String? {
    val dbFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    val newFormat = SimpleDateFormat("dd-mm-yy hh:mm a", Locale.US)
    var strDateTime = inputDate
    var date: Date? = null
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

fun Activity.showHideToolbar(isNeedHide: Boolean) {
    try {
        if (isNeedHide)
            (this as AppCompatActivity).supportActionBar!!.hide()
        else
            (this as AppCompatActivity).supportActionBar!!.show()
    } catch (e: Exception) {
    }
}

fun encodeData(input: String): String {
    try {
        val data = input.toByteArray(charset("UTF-8"))
        return Base64.encodeToString(data, Base64.DEFAULT)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return input
}

fun decodeData(input: String): String {
    try {
        val data: ByteArray = Base64.decode(input, Base64.DEFAULT)
        return String(data, charset("UTF-8"))
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return input
}

fun logPrint(any: Any?) {
    if (BuildConfig.DEBUG)
        Log.d("010101", "\n\nLogData = " + Gson().toJson(any) + "\n\n\n")
}

fun gAppVersion(): String {
    return try {
        BuildConfig.VERSION_CODE.toString()
    } catch (e: Exception) {
        ""
    }
}

