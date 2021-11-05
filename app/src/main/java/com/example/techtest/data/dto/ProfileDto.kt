package com.example.techtest.data.dto

import com.example.techtest.domain.entities.Profile
import com.google.gson.annotations.SerializedName


data class ProfileDto(

    @SerializedName("id") var id: String,
    @SerializedName("title") var title: String,
    @SerializedName("firstName") var firstName: String,
    @SerializedName("lastName") var lastName: String,
    @SerializedName("picture") var picture: String
) {

    fun toProfile(): Profile {
        return Profile(
            id,
            title,
            firstName,
            lastName,
            picture
        )
    }
}