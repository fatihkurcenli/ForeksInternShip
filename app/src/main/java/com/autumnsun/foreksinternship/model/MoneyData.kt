package com.autumnsun.foreksinternship.model



import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MoneyData(
    @SerializedName("l")
    val moneyVariableList: List<MoneyVariable>,
    @SerializedName("z")
    val z: String
):Serializable