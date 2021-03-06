package com.example.techtest.data.remote

import com.example.base.utils.AppLog
import com.example.base.utils.Resource
import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            AppLog.logD("fred : $response")
            if (response.isSuccessful) {
                val body = response.body()
                AppLog.logD("fred : $body")
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        AppLog.logD("fred : $message")
        return Resource.error("Network call has failed for a following reason: $message")
    }

}