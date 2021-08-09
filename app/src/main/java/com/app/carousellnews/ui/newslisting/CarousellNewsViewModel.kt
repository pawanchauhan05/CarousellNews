package com.app.carousellnews.ui.newslisting

import androidx.lifecycle.*
import com.app.carousellnews.data.source.IDataRepository
import com.app.carousellnews.pojo.ResultState
import com.app.carousellnews.pojo.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarousellNewsViewModel @Inject constructor(
    private val dataRepository: IDataRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _newsList = MutableLiveData<ResultState>()
    val newsList: LiveData<ResultState> = _newsList

    fun getCarousellNews(sortType: SortType) {
        _dataLoading.postValue(true)
        viewModelScope.launch(dispatcher) {
            dataRepository.getCarousellNews(sortType).collect {
                _newsList.postValue(it)
            }
        }.invokeOnCompletion {
            _dataLoading.postValue(false)
        }
    }

}

@Suppress("UNCHECKED_CAST")
class CarousellNewsViewModelFactory @Inject constructor(
    private val dataRepository: IDataRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (CarousellNewsViewModel(dataRepository, dispatcher) as T)
}