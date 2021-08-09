package com.app.carousellnews

import com.app.carousellnews.pojo.Carousell
import java.net.SocketTimeoutException

object FakeResponseUtility {
    private val ex : Exception = SocketTimeoutException("TIMEOUT ERROR!")
    private val emptyList = emptyList<Carousell>()

    fun getResponseWithListItems() : MutableList<Carousell> {
        val carousell1 = Carousell(id = "121", title = "Carousell is launching its own digital wallet to improve payments for its users",
            description = "Due to launch next month in Singapore, CarouPay will allow buyers and sellers to complete transactions without leaving the Carousell app",
            banner_url = "https://storage.googleapis.com/carousell-interview-assets/android/images/carousell-siu-rui-ceo-tia-sg-2018.jpg",
            time_created = 1532853058,
            rank = 2)

        val carousell2 = Carousell(id = "122", title = "Southeast Asia-based mobile listings startup Carousell raises \$85M",
            description = "The Team Sky rider, 32, follows Sir Bradley Wiggins in 2012 and four-time Tour champion",
            banner_url = "https://storage.googleapis.com/carousell-interview-assets/android/images/carousell-hero-image_10june.png",
            time_created = 1532939458,
            rank = 5)

        val carousell3 = Carousell(id = "123", title = "Tour de France: Geraint Thomas wins as Chris Froome finishes third",
            description = "Carousell, the Singapore-based mobile listing service that operates across Southeast Asia",
            banner_url = "https://storage.googleapis.com/carousell-interview-assets/android/images/_102749437_thomas_epa.jpg",
            time_created = 1530322670,
            rank = 1)

        return mutableListOf<Carousell>(carousell1, carousell2, carousell3)
    }

    fun getResponseWithError() : Exception {
        return ex
    }

    fun getResponseWithEmptyList() : MutableList<Carousell> {
        return emptyList as MutableList<Carousell>
    }

    fun getResponseWithRecentList() : MutableList<Carousell> {
        val carousell1 = Carousell(id = "121", title = "Carousell is launching its own digital wallet to improve payments for its users",
            description = "Due to launch next month in Singapore, CarouPay will allow buyers and sellers to complete transactions without leaving the Carousell app",
            banner_url = "https://storage.googleapis.com/carousell-interview-assets/android/images/carousell-siu-rui-ceo-tia-sg-2018.jpg",
            time_created = 1532853058,
            rank = 2)

        val carousell2 = Carousell(id = "122", title = "Southeast Asia-based mobile listings startup Carousell raises \$85M",
            description = "The Team Sky rider, 32, follows Sir Bradley Wiggins in 2012 and four-time Tour champion",
            banner_url = "https://storage.googleapis.com/carousell-interview-assets/android/images/carousell-hero-image_10june.png",
            time_created = 1532939458,
            rank = 5)

        val carousell3 = Carousell(id = "123", title = "Tour de France: Geraint Thomas wins as Chris Froome finishes third",
            description = "Carousell, the Singapore-based mobile listing service that operates across Southeast Asia",
            banner_url = "https://storage.googleapis.com/carousell-interview-assets/android/images/_102749437_thomas_epa.jpg",
            time_created = 1530322670,
            rank = 1)

        val list = mutableListOf<Carousell>(carousell1, carousell2, carousell3)
        list.sortBy { it.rank }
        return list
    }

    fun getResponseWithPopularList() : MutableList<Carousell> {
        val carousell1 = Carousell(id = "121", title = "Carousell is launching its own digital wallet to improve payments for its users",
            description = "Due to launch next month in Singapore, CarouPay will allow buyers and sellers to complete transactions without leaving the Carousell app",
            banner_url = "https://storage.googleapis.com/carousell-interview-assets/android/images/carousell-siu-rui-ceo-tia-sg-2018.jpg",
            time_created = 1532853058,
            rank = 2)

        val carousell2 = Carousell(id = "122", title = "Southeast Asia-based mobile listings startup Carousell raises \$85M",
            description = "The Team Sky rider, 32, follows Sir Bradley Wiggins in 2012 and four-time Tour champion",
            banner_url = "https://storage.googleapis.com/carousell-interview-assets/android/images/carousell-hero-image_10june.png",
            time_created = 1532939458,
            rank = 5)

        val carousell3 = Carousell(id = "123", title = "Tour de France: Geraint Thomas wins as Chris Froome finishes third",
            description = "Carousell, the Singapore-based mobile listing service that operates across Southeast Asia",
            banner_url = "https://storage.googleapis.com/carousell-interview-assets/android/images/_102749437_thomas_epa.jpg",
            time_created = 1530322670,
            rank = 1)

        val list = mutableListOf<Carousell>(carousell1, carousell2, carousell3)
        list.sortBy { it.time_created }
        return list
    }
}