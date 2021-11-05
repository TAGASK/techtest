package com.example.techtest.domain.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ProfilesDetails")
data class ProfileDetails(

    @PrimaryKey
    @SerializedName("id") var id: String,
    @SerializedName("title") var title: String,
    @SerializedName("firstName") var firstName: String,
    @SerializedName("lastName") var lastName: String,
    @SerializedName("picture") var picture: String,
    @SerializedName("gender") var gender: String,
    @SerializedName("email") var email: String,
    @SerializedName("dateOfBirth") var dateOfBirth: String,
    @SerializedName("phone") var phone: String,
    @Embedded
    @SerializedName("location") var location: Location,
    @SerializedName("registerDate") var registerDate: String,
    @SerializedName("updatedDate") var updatedDate: String

)