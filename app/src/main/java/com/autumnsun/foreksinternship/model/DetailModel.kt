package com.autumnsun.foreksinternship.model


import com.google.gson.annotations.SerializedName

data class DetailModel(
    @SerializedName("d")
    val d: List<D>,
    @SerializedName("def")
    val def: String
)