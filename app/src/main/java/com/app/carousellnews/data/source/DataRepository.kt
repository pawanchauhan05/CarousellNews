package com.app.carousellnews.data.source

import com.app.carousellnews.data.source.local.ILocalDataSource
import com.app.carousellnews.data.source.remote.IRemoteDataSource
import com.app.carousellnews.pojo.ResultState
import com.app.carousellnews.pojo.SortType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DataRepository(
    public val localDataSource: ILocalDataSource,
    public val remoteDataSource: IRemoteDataSource,
    public val dispatcher: CoroutineDispatcher
) : IDataRepository {

    override suspend fun getCarousellNews(sortType: SortType): Flow<ResultState> = flow {
        try {
            val localList = localDataSource.getAllCarousellNews()
            if (localList.isNotEmpty()) {
                emit(ResultState.Success(localList))
            }
            val response = remoteDataSource.getCarousellNews()
            when(sortType) {
                SortType.POPULAR -> {
                    val popularList = response.sortedBy { it.rank } as MutableList
                    emit(ResultState.Success(popularList)).also {
                        localDataSource.deleteAll()
                        localDataSource.insertCarousellNews(popularList)
                    }
                }

                SortType.RECENT -> {
                    val recentList = response.sortedBy { it.time_created } as MutableList
                    emit(ResultState.Success(recentList)).also {
                        localDataSource.deleteAll()
                        localDataSource.insertCarousellNews(recentList)
                    }
                }

                SortType.RESET -> {
                    emit(ResultState.Success(response)).also {
                        localDataSource.deleteAll()
                        localDataSource.insertCarousellNews(response)
                    }
                }
            }
        } catch (ex: Exception) {
            emit(ResultState.Failure(ex))
        }
    }
}