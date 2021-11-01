package com.example.techtest.data.entities


import com.google.gson.annotations.SerializedName


data class ProfileList(

    @SerializedName("data") var data: List<Profile>,
    @SerializedName("total") var total: Int,
    @SerializedName("page") var page: Int,
    @SerializedName("limit") var limit: Int

)