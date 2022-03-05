package com.bd.dana.hiltpractice.api.models.tv_show


import com.google.gson.annotations.SerializedName

data class Network(
    @SerializedName("country")
    var country: Country = Country(),
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = ""
)