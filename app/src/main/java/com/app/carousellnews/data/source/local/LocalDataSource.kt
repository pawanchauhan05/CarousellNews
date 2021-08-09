package com.app.carousellnews.data.source.local

import com.app.carousellnews.pojo.Carousell
import kotlinx.coroutines.CoroutineDispatcher

class LocalDataSource internal constructor(
    private val newsDao: NewsDao,
    private val dispatcher: CoroutineDispatcher
)  : ILocalDataSource {
    override fun insertCarousellNews(carousellNewsDataList: MutableList<Carousell>) {
        newsDao.insertNews(carousellNewsDataList)
    }

    override fun getAllCarousellNews(): MutableList<Carousell> {
        return newsDao.getAllNews()
    }

    override fun deleteAll() {
        newsDao.deleteAll()
    }
}