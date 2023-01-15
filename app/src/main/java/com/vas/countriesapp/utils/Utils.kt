package com.vas.countriesapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun <T : Any> ViewModel.doSingleRequest(
    query: suspend () -> T,
    doOnLoading: () -> Unit = {},
    doOnSuccess: (T) -> Unit,
    doOnError: (Exception) -> Unit = {}
) {
    viewModelScope.launch {
        doOnLoading.invoke()
        try {
            val response = withContext(Dispatchers.IO) { query.invoke() }
            doOnSuccess.invoke(response)
        } catch (e: Exception) {
            doOnError.invoke(e)
        }
    }
}

data class RemoteResult<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): RemoteResult<T> {
            return RemoteResult(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String?, data: T? = null): RemoteResult<T> {
            return RemoteResult(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): RemoteResult<T> {
            return RemoteResult(Status.LOADING, data, null)
        }
    }
}