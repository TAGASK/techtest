package com.example.techtest.domain.repository

import com.example.base.utils.performGetOperation
import com.example.techtest.domain.locale.AppDatabase
import com.example.techtest.domain.remote.ProfileRemoteDataSource
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

    fun getUsers(page: Int) = performGetOperation(
        databaseQuery = { localDataSource.profileDao().getUsers() },
        networkCall = { remoteDataSource.getUsers(page) },
        saveCallResult = { localDataSource.profileDao().insertAll(it.data) }
    )
}