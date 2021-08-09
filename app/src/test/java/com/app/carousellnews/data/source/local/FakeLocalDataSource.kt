package com.app.carousellnews.data.source.local

import com.app.carousellnews.pojo.Carousell

class FakeLocalDataSource(private var newsList: MutableList<Carousell> = mutableListOf()) : ILocalDataSource {

    override fun insertCarousellNews(carousellNewsDataList: MutableList<Carousell>) {
        newsList.addAll(carousellNewsDataList)
    }

    override fun getAllCarousellNews(): MutableList<Carousell> {
        return newsList
    }

    override fun deleteAll() {
        newsList.clear()
    }
}