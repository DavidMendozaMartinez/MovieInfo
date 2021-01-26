package com.davidmendozamartinez.movieinfo.presentation.util

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun <T> MutableLiveData<T>.setOnGetDataListener(
    scope: CoroutineScope,
    onSuccess: suspend CoroutineScope.() -> T,
    onLoading: () -> Unit = {},
    onError: (String?) -> Unit = {}
) {
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError.invoke(throwable.message)
        throwable.printStackTrace()
    }
    scope.launch(coroutineExceptionHandler) {
        onLoading.invoke()
        try {
            value = onSuccess.invoke(this)
        } catch (exception: Exception) {
            onError.invoke(exception.message)
            exception.printStackTrace()
        }
    }
}