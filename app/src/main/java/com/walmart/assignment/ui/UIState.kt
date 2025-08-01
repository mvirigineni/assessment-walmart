package com.walmart.assignment.ui

sealed class UIState<out T> {
    data object Loading : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Error(val message: String) : UIState<Nothing>()
    data object Empty : UIState<Nothing>()
} 