package com.example.techtest.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Profiles")
data class Profile(

    @PrimaryKey
    @SerializedName("id") var id: String,
    @SerializedName("title") var title: String,
    @SerializedName("firstName") var firstName: String,
    @SerializedName("lastName") var lastName: String,
    @SerializedName("picture") var picture: String

)