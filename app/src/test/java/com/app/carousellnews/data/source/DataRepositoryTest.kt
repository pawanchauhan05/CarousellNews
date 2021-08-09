package com.app.carousellnews.data.source

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.carousellnews.FakeResponseUtility
import com.app.carousellnews.MainCoroutineRule
import com.app.carousellnews.data.source.local.FakeLocalDataSource
import com.app.carousellnews.data.source.local.ILocalDataSource
import com.app.carousellnews.data.source.local.LocalDataSource
import com.app.carousellnews.data.source.remote.FakeRemoteDataSource
import com.app.carousellnews.data.source.remote.IRemoteDataSource
import com.app.carousellnews.pojo.ResultState
import com.app.carousellnews.pojo.SortType
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
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
class DataRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Inject
    lateinit var fakeLocalDataSource: ILocalDataSource

    @Inject
    lateinit var fakeRemoteDataSource: IRemoteDataSource

    @Inject
    lateinit var coDispatcher: CoroutineDispatcher

    private lateinit var dataRepository: DataRepository

    @Before
    fun setUp() {
        // Populate @Inject fields in test class
        hiltRule.inject()
        dataRepository = DataRepository(fakeLocalDataSource, fakeRemoteDataSource, coDispatcher)
    }

    @Test
    fun getCarousellNews_shouldReturnSuccess_withNormalListItem() = mainCoroutineRule.runBlockingTest {
        val sortType = SortType.RESET
        val list = dataRepository.getCarousellNews(sortType).toList()

        Assert.assertEquals(
            list, listOf(
                ResultState.Success(FakeResponseUtility.getResponseWithListItems())
            )
        )

        Assert.assertEquals(
            dataRepository.localDataSource.getAllCarousellNews(),
            FakeResponseUtility.getResponseWithListItems()
        )
    }

    @Test
    fun getCarousellNews_shouldReturnError_forNormalListItem() = mainCoroutineRule.runBlockingTest {
        (fakeRemoteDataSource as FakeRemoteDataSource).setStatus(FakeRemoteDataSource.Data.SHOULD_RETURN_ERROR)

        val sortType = SortType.RESET
        val list = dataRepository.getCarousellNews(sortType).toList()

        Assert.assertEquals(
            list, listOf(
                ResultState.Failure(FakeResponseUtility.getResponseWithError())
            )
        )
    }

    @Test
    fun getCarousellNews_shouldReturnSuccess_forRecentListItem() = mainCoroutineRule.runBlockingTest {
        val sortType = SortType.RECENT
        val list = dataRepository.getCarousellNews(sortType).toList()

        Assert.assertEquals(
            list, listOf(
                ResultState.Success(FakeResponseUtility.getResponseWithRecentList())
            )
        )

        Assert.assertEquals(
            dataRepository.localDataSource.getAllCarousellNews(),
            FakeResponseUtility.getResponseWithRecentList()
        )
    }

    @Test
    fun getCarousellNews_shouldReturnSuccess_forPopularListItem() = mainCoroutineRule.runBlockingTest {
        val sortType = SortType.POPULAR
        val list = dataRepository.getCarousellNews(sortType).toList()

        Assert.assertEquals(
            list, listOf(
                ResultState.Success(FakeResponseUtility.getResponseWithPopularList())
            )
        )

        Assert.assertEquals(
            dataRepository.localDataSource.getAllCarousellNews(),
            FakeResponseUtility.getResponseWithPopularList()
        )
    }

    @Test
    fun getCarousellNews_shouldReturnSuccess_forPopularListItem_withExistingLocalData() = mainCoroutineRule.runBlockingTest {
        val listItem = FakeResponseUtility.getResponseWithListItems()
        (fakeLocalDataSource as FakeLocalDataSource).insertCarousellNews(listItem)

        val sortType = SortType.POPULAR
        val list = dataRepository.getCarousellNews(sortType).toList()

        Assert.assertEquals(
            list, listOf(
                ResultState.Success(fakeLocalDataSource.getAllCarousellNews()),
                ResultState.Success(FakeResponseUtility.getResponseWithPopularList())
            )
        )

        Assert.assertEquals(
            dataRepository.localDataSource.getAllCarousellNews(),
            FakeResponseUtility.getResponseWithPopularList()
        )
    }
}