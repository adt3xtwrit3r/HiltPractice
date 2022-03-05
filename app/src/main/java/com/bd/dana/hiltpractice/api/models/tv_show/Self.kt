package com.bd.dana.hiltpractice.api.models.tv_show


import com.google.gson.annotations.SerializedName

data class Self(
    @SerializedName("href")
    var href: String = ""
)