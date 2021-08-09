package com.app.carousellnews.data.source

import com.app.carousellnews.pojo.ResultState
import com.app.carousellnews.pojo.SortType
import kotlinx.coroutines.flow.Flow
import java.util.*

interface IDataRepository {

    suspend fun getCarousellNews(sortType: SortType) : Flow<ResultState>
}
