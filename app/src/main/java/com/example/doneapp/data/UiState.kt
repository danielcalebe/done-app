package com.example.doneapp.data

sealed class UiState<out T> {
    data class Success<out T>(val result: T): UiState<T>()
    data class Error(val e: Exception, val msg: String): UiState<Nothing>()
    object Loading: UiState<Nothing>()
}