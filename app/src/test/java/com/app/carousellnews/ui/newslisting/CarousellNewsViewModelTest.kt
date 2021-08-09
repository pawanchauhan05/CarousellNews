package com.app.carousellnews.ui.newslisting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.carousellnews.FakeResponseUtility
import com.app.carousellnews.MainCoroutineRule
import com.app.carousellnews.data.source.DataRepository
import com.app.carousellnews.data.source.local.ILocalDataSource
import com.app.carousellnews.data.source.remote.FakeRemoteDataSource
import com.app.carousellnews.data.source.remote.IRemoteDataSource
import com.app.carousellnews.getOrAwaitValue
import com.app.carousellnews.pojo.ResultState
import com.app.carousellnews.pojo.SortType
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import junit.framework.TestCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@ExperimentalCoroutinesApi
class CarousellNewsViewModelTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fakeLocalDataSource: ILocalDataSource

    @Inject
    lateinit var fakeRemoteDataSource: IRemoteDataSource

    @Inject
    lateinit var dispatcher: CoroutineDispatcher

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var dataRepository: DataRepository
    private lateinit var carousellNewsViewModel: CarousellNewsViewModel

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        hiltRule.inject()
        dataRepository = DataRepository(fakeLocalDataSource, fakeRemoteDataSource, dispatcher)
        carousellNewsViewModel = CarousellNewsViewModel(dataRepository, dispatcher)
    }

    @Test
    fun getCarousellNews_shouldReturnSuccess() = (dispatcher as TestCoroutineDispatcher).runBlockingTest {
        pauseDispatcher()
        carousellNewsViewModel.getCarousellNews(SortType.RESET)

        val data1 = carousellNewsViewModel.dataLoading.getOrAwaitValue()

        Assert.assertEquals(data1 , true)

        resumeDispatcher()

        val data2 = carousellNewsViewModel.newsList.getOrAwaitValue()
        Assert.assertEquals(data2 , ResultState.Success(FakeResponseUtility.getResponseWithListItems()))

        val data3 = carousellNewsViewModel.dataLoading.getOrAwaitValue()
        Assert.assertEquals(data3 , false)
    }

    @Test
    fun getCarousellNews_shouldReturnError() = (dispatcher as TestCoroutineDispatcher).runBlockingTest {
        (fakeRemoteDataSource as FakeRemoteDataSource).setStatus(FakeRemoteDataSource.Data.SHOULD_RETURN_ERROR)
        pauseDispatcher()
        carousellNewsViewModel.getCarousellNews(SortType.RESET)

        val data1 = carousellNewsViewModel.dataLoading.getOrAwaitValue()

        Assert.assertEquals(data1 , true)

        resumeDispatcher()

        val data2 = carousellNewsViewModel.newsList.getOrAwaitValue()
        Assert.assertEquals(data2 , ResultState.Failure(FakeResponseUtility.getResponseWithError()))

        val data3 = carousellNewsViewModel.dataLoading.getOrAwaitValue()
        Assert.assertEquals(data3 , false)
    }

    @Test
    fun getCarousellNews_shouldReturnSuccess_recentList() = (dispatcher as TestCoroutineDispatcher).runBlockingTest {
        pauseDispatcher()
        carousellNewsViewModel.getCarousellNews(SortType.RECENT)

        val data1 = carousellNewsViewModel.dataLoading.getOrAwaitValue()

        Assert.assertEquals(data1 , true)

        resumeDispatcher()

        val data2 = carousellNewsViewModel.newsList.getOrAwaitValue()
        Assert.assertEquals(data2 , ResultState.Success(FakeResponseUtility.getResponseWithRecentList()))

        val data3 = carousellNewsViewModel.dataLoading.getOrAwaitValue()
        Assert.assertEquals(data3 , false)
    }
}