package com.example.techtest.data.remote

import com.example.techtest.data.dto.ProfileDetailsDto
import com.example.techtest.data.dto.ProfileListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileService {

    @GET("user")
    suspend fun getUsers(@Query("page") id: Int): Response<ProfileListDto>

    @GET("user/{id}")
    suspend fun getUser(@Path("id") id: String): Response<ProfileDetailsDto>

}