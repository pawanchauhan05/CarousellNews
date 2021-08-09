package com.app.carousellnews.data.source.remote

import com.app.carousellnews.FakeResponseUtility
import com.app.carousellnews.pojo.Carousell

class FakeRemoteDataSource:  IRemoteDataSource {

    enum class Data {
        SHOULD_RETURN_ERROR,
        SHOULD_RETURN_LIST_WITH_ITEM,
        SHOULD_RETURN_EMPTY_LIST
    }

    private var status = Data.SHOULD_RETURN_LIST_WITH_ITEM

    fun setStatus(value: Data) {
        status = value
    }

    override suspend fun getCarousellNews(): MutableList<Carousell> {
        return when(status) {
            Data.SHOULD_RETURN_ERROR -> throw FakeResponseUtility.getResponseWithError()
            Data.SHOULD_RETURN_LIST_WITH_ITEM -> FakeResponseUtility.getResponseWithListItems()
            Data.SHOULD_RETURN_EMPTY_LIST -> FakeResponseUtility.getResponseWithEmptyList()
        }
    }
}