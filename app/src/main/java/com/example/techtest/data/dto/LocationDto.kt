package com.example.techtest.data.dto


import com.example.techtest.domain.entities.Location
import com.google.gson.annotations.SerializedName


data class LocationDto(

    @SerializedName("street") var street: String,
    @SerializedName("city") var city: String,
    @SerializedName("state") var state: String,
    @SerializedName("country") var country: String,
    @SerializedName("timezone") var timezone: String

) {
    fun toLocation(): Location {
        return Location(
            street,
            city,
            state,
            country,
            timezone
        )
    }
}