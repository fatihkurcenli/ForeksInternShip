package com.autumnsun.foreksinternship.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GraphModelItem(
    @SerializedName("c")
    val c: Double,
    @SerializedName("d")
    val d: Long
):Serializable