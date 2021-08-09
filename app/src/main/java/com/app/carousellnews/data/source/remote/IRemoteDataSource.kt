package com.app.carousellnews.data.source.remote

import com.app.carousellnews.pojo.Carousell

interface IRemoteDataSource {

    suspend fun getCarousellNews(): MutableList<Carousell>
}