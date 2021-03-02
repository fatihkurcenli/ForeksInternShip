package com.autumnsun.foreksinternship.model


import com.google.gson.annotations.SerializedName

data class Fields(
    @SerializedName("buy")
    val buy: String,
    @SerializedName("ddi")
    val ddi: String,
    @SerializedName("hig")
    val hig: String,
    @SerializedName("las")
    val las: String,
    @SerializedName("lmh")
    val lmh: String,
    @SerializedName("lml")
    val lml: String,
    @SerializedName("lmp")
    val lmp: String,
    @SerializedName("low")
    val low: String,
    @SerializedName("lwh")
    val lwh: String,
    @SerializedName("lwl")
    val lwl: String,
    @SerializedName("lwp")
    val lwp: String,
    @SerializedName("lyh")
    val lyh: String,
    @SerializedName("lyl")
    val lyl: String,
    @SerializedName("lyp")
    val lyp: String,
    @SerializedName("MP")
    val mP: String,
    @SerializedName("1mh")
    val mh: String,
    @SerializedName("1ml")
    val ml: String,
    @SerializedName("1mt")
    val mt: String,
    @SerializedName("mtu")
    val mtu: String,
    @SerializedName("1mv")
    val mv: String,
    @SerializedName("mvo")
    val mvo: String,
    @SerializedName("ope")
    val ope: String,
    @SerializedName("pdd")
    val pdd: String,
    @SerializedName("sel")
    val sel: String,
    @SerializedName("tur")
    val tur: String,
    @SerializedName("vol")
    val vol: String,
    @SerializedName("WP")
    val wP: String,
    @SerializedName("1wh")
    val wh: String,
    @SerializedName("1wl")
    val wl: String,
    @SerializedName("wpd")
    val wpd: String,
    @SerializedName("1wt")
    val wt: String,
    @SerializedName("wtu")
    val wtu: String,
    @SerializedName("1wv")
    val wv: String,
    @SerializedName("wvo")
    val wvo: String,
    @SerializedName("YP")
    val yP: String,
    @SerializedName("1yh")
    val yh: String,
    @SerializedName("1yl")
    val yl: String
)