package com.example.techtest.domain.repository

import com.example.base.utils.Resource
import com.example.techtest.domain.entities.Profile
import com.example.techtest.domain.entities.ProfileDetails
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getUsers(page: Int): Flow<Resource<List<Profile>>>
    fun getUser(id: String): Flow<Resource<ProfileDetails>>
}
