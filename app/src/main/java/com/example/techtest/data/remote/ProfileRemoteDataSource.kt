package com.example.techtest.data.remote

import javax.inject.Inject

class ProfileRemoteDataSource @Inject constructor(
    private val profileService: ProfileService
) : BaseDataSource() {

    suspend fun getUsers(page: Int) = getResult { profileService.getUsers(page) }
    suspend fun getUser(id: String) = getResult { profileService.getUser(id) }
}