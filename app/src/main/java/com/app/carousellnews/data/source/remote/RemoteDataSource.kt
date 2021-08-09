package com.app.carousellnews.data.source.remote

import com.app.carousellnews.pojo.Carousell


class RemoteDataSource(private val remoteApi: RemoteApi) : IRemoteDataSource {
    override suspend fun getCarousellNews(): MutableList<Carousell> {
        return remoteApi.getCarousellNews()
    }
}