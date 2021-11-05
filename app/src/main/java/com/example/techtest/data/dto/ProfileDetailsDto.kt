package com.example.techtest.data.dto

import com.example.techtest.domain.entities.ProfileDetails
import com.google.gson.annotations.SerializedName


data class ProfileDetailsDto(

    @SerializedName("id") var id: String,
    @SerializedName("title") var title: String,
    @SerializedName("firstName") var firstName: String,
    @SerializedName("lastName") var lastName: String,
    @SerializedName("picture") var picture: String,
    @SerializedName("gender") var gender: String,
    @SerializedName("email") var email: String,
    @SerializedName("dateOfBirth") var dateOfBirth: String,
    @SerializedName("phone") var phone: String,
    @SerializedName("location") var locationDto: LocationDto,
    @SerializedName("registerDate") var registerDate: String,
    @SerializedName("updatedDate") var updatedDate: String

) {
    fun toProfileDetails(): ProfileDetails {
        return ProfileDetails(
            id,
            title,
            firstName,
            lastName,
            picture,
            gender,
            email,
            dateOfBirth,
            phone,
            locationDto.toLocation(),
            registerDate,
            updatedDate
        )
    }
}