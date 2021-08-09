package com.app.carousellnews.data.source.local

import com.app.carousellnews.pojo.Carousell

interface ILocalDataSource {

    fun insertCarousellNews(carousellNewsDataList : MutableList<Carousell>)

    fun getAllCarousellNews(): MutableList<Carousell>

    fun deleteAll()
}