package com.example.techtest.domain.remote

import com.example.techtest.data.entities.ProfileDetails
import com.example.techtest.data.entities.ProfileList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileService {

    @GET("user")
    suspend fun getUsers(@Query("page") id: Int): Response<ProfileList>

    @GET("user/{id}")
    suspend fun getUser(@Path("id") id: String): Response<ProfileDetails>

}