package com.example.techtest.data.remote

import javax.inject.Inject

class ProfileRemoteDataSource @Inject constructor(
    private val profileService: ProfileService
) : BaseDataSource() {

    suspend fun getUsers() = getResult { profileService.getUsers() }
    suspend fun getUser(id: String) = getResult { profileService.getUser(id) }
}