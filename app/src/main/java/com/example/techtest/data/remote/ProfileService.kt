package com.example.techtest.data.remote

import com.example.techtest.data.entities.ProfileDetails
import com.example.techtest.data.entities.ProfileList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileService {

    @GET("user")
    suspend fun getUsers(): Response<ProfileList>

    @GET("user/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<ProfileDetails>

}