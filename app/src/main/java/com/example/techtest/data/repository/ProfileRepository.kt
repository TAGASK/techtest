package com.example.techtest.data.repository

import com.example.techtest.data.locale.AppDatabase
import com.example.techtest.data.remote.ProfileRemoteDataSource
import com.example.techtest.utils.performGetOperation
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val remoteDataSource: ProfileRemoteDataSource,
    private val localDataSource: AppDatabase
) {

    fun getUser(id: String) = performGetOperation(
        databaseQuery = { localDataSource.profileDetailsDao().getUser(id) },
        networkCall = { remoteDataSource.getUser(id) },
        saveCallResult = { localDataSource.profileDetailsDao().insert(it) }
    )

    fun getUsers() = performGetOperation(
        databaseQuery = { localDataSource.profileDao().getUsers() },
        networkCall = { remoteDataSource.getUsers() },
        saveCallResult = { localDataSource.profileDao().insertAll(it.data) }
    )
}