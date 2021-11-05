package com.example.techtest.data.dto


import com.example.techtest.domain.entities.Profile
import com.google.gson.annotations.SerializedName


data class ProfileListDto(

    @SerializedName("data") var data: List<ProfileDto>,
    @SerializedName("total") var total: Int,
    @SerializedName("page") var page: Int,
    @SerializedName("limit") var limit: Int
) {

    fun toProfileList(): List<Profile> {
        return data.map { it.toProfile() }
    }

}