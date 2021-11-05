package com.example.techtest.data.repository

import com.example.base.utils.performGetOperation
import com.example.techtest.data.locale.AppDatabase
import com.example.techtest.data.remote.ProfileRemoteDataSource
import com.example.techtest.domain.repository.IRepository
import javax.inject.Inject

open class ProfileRepository @Inject constructor(
    private val remoteDataSource: ProfileRemoteDataSource,
    private val localDataSource: AppDatabase
) : IRepository {

    override fun getUser(id: String) = performGetOperation(
        databaseQuery = { localDataSource.profileDetailsDao().getUser(id) },
        networkCall = { remoteDataSource.getUser(id) },
        saveCallResult = { localDataSource.profileDetailsDao().insert(it.toProfileDetails()) }
    )

    override fun getUsers(page: Int) = performGetOperation(
        databaseQuery = { localDataSource.profileDao().getUsers() },
        networkCall = { remoteDataSource.getUsers(page) },
        saveCallResult = { localDataSource.profileDao().insertAll(it.toProfileList()) }
    )
}