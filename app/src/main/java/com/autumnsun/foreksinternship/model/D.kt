package com.autumnsun.foreksinternship.model


import com.google.gson.annotations.SerializedName

data class D(
    @SerializedName("clo")
    val clo: String,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("fields")
    val fields: Fields,
    @SerializedName("type")
    val type: Int
)