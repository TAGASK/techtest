package com.example.techtest.utils

import kotlinx.coroutines.flow.*

fun <T, A> performGetOperation(
    databaseQuery: () -> Flow<T>,
    networkCall: suspend () -> Resource<A>,
    saveCallResult: suspend (A) -> Unit
): Flow<Resource<T>> =
    flow {
        emit(Resource.loading())
        emit(Resource.success(databaseQuery.invoke().first()))

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)
        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }
        emitAll(databaseQuery.invoke().map { Resource.success(it) })
    }