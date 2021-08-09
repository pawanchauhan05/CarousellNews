package com.app.carousellnews.data.source.remote

import com.app.carousellnews.pojo.Carousell
import com.app.carousellnews.pojo.ServerResponse
import retrofit2.http.GET

interface RemoteApi {

    @GET("carousell-interview-assets/android/carousell_news.json")
    suspend fun getCarousellNews(): MutableList<Carousell>
}