package com.example.lts.datasource.remote.interceptor

import com.example.lts.MyApplication
import okhttp3.Interceptor
import okhttp3.Response

import java.io.IOException

class HeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val token = MyApplication.instance.authToken
//        Timber.d("token: $token url: ${originalRequest.url}")

        val requestBuilder = originalRequest.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Connection", "close")
            .addHeader("x-auth-token", token?:"")
//            .addHeader("application_version_code", BuildConfig.VERSION_CODE.toString())
//            .addHeader("application_version_name", BuildConfig.VERSION_NAME)

//            .addHeader("timezone", getTimeZoneId())

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}