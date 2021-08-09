package com.app.carousellnews.pojo

sealed class ResultState {
    data class Success(val dataList : MutableList<Carousell>) : ResultState()
    data class Failure(val exception : Exception) : ResultState()
    data class Progress(val isShow : Boolean) : ResultState()
}
