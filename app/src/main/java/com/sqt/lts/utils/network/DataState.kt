package com.example.lts.utils.network

import java.io.Serializable

sealed class DataState<out R> {
    data class Success<out DATA>(val data: DATA, val message: String? = "") : DataState<DATA>()
    data class Error(val exception: Exception) : DataState<Nothing>()
    data object Loading : DataState<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success $data"
            is Error -> "Failure ${exception.message}"
            Loading -> "Loading"
        }
    }
}
