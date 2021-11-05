package com.example.techtest.data.remote

import com.example.techtest.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("app-id", BuildConfig.API_APP_ID)
                .build()
        )
    }
}