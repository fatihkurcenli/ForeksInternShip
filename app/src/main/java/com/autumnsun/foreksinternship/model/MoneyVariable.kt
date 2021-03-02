package com.autumnsun.foreksinternship.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MoneyVariable(
    @SerializedName("buy")
    val buy: String,
    @SerializedName("clo")
    val clockClo: String,
    @SerializedName("cod")
    val codKod: String,
    @SerializedName("ddi")
    val farkDdi: String,
    @SerializedName("def")
    val defAciklama: String,
    @SerializedName("hig")
    val highYuksek: String,
    @SerializedName("las")
    val lasSon: String,
    @SerializedName("low")
    val lowGDusuk: String,
    @SerializedName("pdc")
    val pdcOGKapanis: String,
    @SerializedName("pdd")
    val pddYuzdeFark: String,
    @SerializedName("rtp")
    val rtpAnlıkData: Boolean,
    @SerializedName("sel")
    val selSatis: String,
    @SerializedName("tke")
    val tkeID: String
):Serializable