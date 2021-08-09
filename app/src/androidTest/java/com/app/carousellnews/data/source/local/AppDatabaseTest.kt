package com.app.carousellnews.data.source.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.carousellnews.pojo.Carousell
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var newsDao: NewsDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        newsDao = appDatabase.getNewsDao()
    }

    @Test
    fun insertAndReadNews() = runBlocking {

        val carousell = Carousell(id = "121", title = "Carousell is launching its own digital wallet to improve payments for its users",
            description = "Due to launch next month in Singapore, CarouPay will allow buyers and sellers to complete transactions without leaving the Carousell app",
            banner_url = "https://storage.googleapis.com/carousell-interview-assets/android/images/carousell-siu-rui-ceo-tia-sg-2018.jpg",
            time_created = 1532853058,
            rank = 2)
        newsDao.insertNews(carousell)

        val newsList = newsDao.getAllNews()
        Assert.assertTrue(newsList.contains(carousell))
    }

    @Test
    fun insertAndReadPhotoList() = runBlocking {
        val carousell1 = Carousell(id = "121", title = "Carousell is launching its own digital wallet to improve payments for its users",
            description = "Due to launch next month in Singapore, CarouPay will allow buyers and sellers to complete transactions without leaving the Carousell app",
            banner_url = "https://storage.googleapis.com/carousell-interview-assets/android/images/carousell-siu-rui-ceo-tia-sg-2018.jpg",
            time_created = 1532853058,
            rank = 2)

        val carousell2 = Carousell(id = "122", title = "Southeast Asia-based mobile listings startup Carousell raises \$85M",
            description = "Carousell, the Singapore-based mobile listing service that operates across Southeast Asia",
            banner_url = "https://storage.googleapis.com/carousell-interview-assets/android/images/carousell-hero-image_10june.png",
            time_created = 1532939458,
            rank = 5)


        val listToInsert = mutableListOf<Carousell>(carousell1, carousell2)
        newsDao.insertNews(listToInsert)
        val newsList = newsDao.getAllNews()
        Assert.assertEquals(newsList, listToInsert)
    }

    @Test
    fun insertAndDeletePhoto() = runBlocking {
        val carousell1 = Carousell(id = "121", title = "Carousell is launching its own digital wallet to improve payments for its users",
            description = "Due to launch next month in Singapore, CarouPay will allow buyers and sellers to complete transactions without leaving the Carousell app",
            banner_url = "https://storage.googleapis.com/carousell-interview-assets/android/images/carousell-siu-rui-ceo-tia-sg-2018.jpg",
            time_created = 1532853058,
            rank = 2)

        newsDao.insertNews(carousell1)
        newsDao.deleteAll()
        val photoList = newsDao.getAllNews()

        Assert.assertTrue(photoList.isEmpty())
    }

    @After
    fun closeDb() {
        appDatabase.close()
    }
}