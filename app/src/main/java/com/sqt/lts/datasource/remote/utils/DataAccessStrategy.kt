package com.example.lts.datasource.remote.utils

import com.example.lts.base.BaseResponse
import com.example.lts.utils.network.DataState
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

fun <DATA, T : BaseResponse<DATA>> safeApiCallWithApiStatus(call: suspend () -> T) = flow {
    emit(DataState.Loading)

//    delay(5000)

    try {
        val response = call()
        if (response.status == 1) {
//            delay(2000)
            emit(DataState.Success(response.data, response.message))
        } else {
            val message = response.message
            throw ApiZeroException(message)
        }
    } catch (e: JsonSyntaxException) {
        Timber.e(e, "invalid json: ")
//        FirebaseCrashlytics.getInstance().recordException(e)
        emit(DataState.Error(e))
    } catch (e: Exception) {
        Timber.e(e, "safeApiCall: ")
//        FirebaseCrashlytics.getInstance().recordException(e)
        emit(DataState.Error(e))
    }
}.flowOn(Dispatchers.IO)
class ApiZeroException(message: String?) : Exception(message)