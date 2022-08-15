package com.mominulcse7.githubclone.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mominulcse7.githubclone.BuildConfig
import com.mominulcse7.githubclone.network.NetworkError
import com.mominulcse7.githubclone.utils.logPrint
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    val networkError = MutableLiveData<String>()
    lateinit var application: Application

    //multipleParameter
    fun <T : Any, U : Any> getResponse(
        requesterMethod: suspend (T, T, T, T, T) -> U,
        parameter1: T,
        parameter2: T,
        parameter3: T,
        parameter4: T,
        parameter5: T,
        onResponseMethod: (response: U) -> Unit
    ) {
        viewModelScope.launch {
            try {
                onResponseMethod(requesterMethod(parameter1, parameter2, parameter3, parameter4, parameter5))
            } catch (errorMsg: Throwable) {
                val errorMessage = NetworkError.getServerResponseMessage(errorMsg, application)
                networkError.value = errorMessage
                Toast.makeText(application, errorMessage, Toast.LENGTH_LONG).show()
                printApiResponse(errorMsg.localizedMessage)
            }
        }
    }

    //singleParameter
    fun <U : Any> getResponse(
        requesterMethod: suspend () -> U,
        onResponseMethod: (response: U) -> Unit
    ) {
        viewModelScope.launch {
            try {
                onResponseMethod(requesterMethod())
            } catch (errorMsg: Throwable) {
                networkError.value = NetworkError.getServerResponseMessage(errorMsg, application)
                printApiResponse(errorMsg.localizedMessage)
            }
        }
    }

    private fun printApiResponse(errorMsg: String?) {
        if (BuildConfig.DEBUG)
            logPrint(errorMsg)
    }

}