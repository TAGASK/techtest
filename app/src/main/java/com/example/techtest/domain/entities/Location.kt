package com.example.techtest.domain.entities


import com.google.gson.annotations.SerializedName


data class Location(

    @SerializedName("street") var street: String,
    @SerializedName("city") var city: String,
    @SerializedName("state") var state: String,
    @SerializedName("country") var country: String,
    @SerializedName("timezone") var timezone: String

)