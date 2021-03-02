package com.autumnsun.foreksinternship.service

import com.autumnsun.foreksinternship.model.DetailModel
import com.autumnsun.foreksinternship.model.GraphModelItem
import com.autumnsun.foreksinternship.model.MoneyData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("list.php")
    fun getData(): Call<MoneyData>

    @GET("graph.php")
    fun graphData(@Query("cod") cod: String): Call<List<GraphModelItem>>

    @GET("detail.php")
    fun getDetail(@Query("cod")cod:String):Call<DetailModel>
}