package com.app.carousellnews.ui.newslisting

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.carousellnews.R
import com.app.carousellnews.data.source.IDataRepository
import com.app.carousellnews.pojo.ResultState
import com.app.carousellnews.pojo.SortType
import com.app.carousellnews.utilities.visibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_carousell_news.*
import kotlinx.coroutines.CoroutineDispatcher
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@AndroidEntryPoint
class CarousellNewsActivity : AppCompatActivity() {

    @Inject
    lateinit var dataRepository: IDataRepository

    @Inject
    lateinit var dispatcher: CoroutineDispatcher

    private lateinit var newsAdapter: NewsAdapter

    private val carousellNewsViewModel by viewModels<CarousellNewsViewModel> {
        CarousellNewsViewModelFactory(dataRepository, dispatcher)
    }
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carousell_news)

        newsAdapter = NewsAdapter(mutableListOf())

        recyclerView.apply {
            layoutManager = linearLayoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = newsAdapter
        }

        initObservers()

        carousellNewsViewModel.getCarousellNews(SortType.RESET)
    }

    private fun initObservers() {
        carousellNewsViewModel.newsList.observe(this, Observer {
            when (it) {
                is ResultState.Success -> {
                    newsAdapter.updateList(it.dataList)
                    if (newsAdapter.list.isEmpty()) {
                        textViewError.apply {
                            text = "No News Found"
                            visibility(true)
                        }
                        recyclerView.visibility(false)
                    } else {
                        recyclerView.visibility(true)
                        textViewError.visibility(false)
                    }
                }
                is ResultState.Failure -> {
                    var errorMessage: String = when (it.exception) {
                        is SocketTimeoutException -> {
                            getString(R.string.timeout_error)
                        }
                        is UnknownHostException -> {
                            getString(R.string.no_internet)
                        }
                        else -> {
                            it.exception.message ?: getString(R.string.something_went_wrong)
                        }
                    }
                    if (newsAdapter.list.isEmpty()) {
                        textViewError.apply {
                            text = errorMessage
                            visibility(true)
                        }
                        recyclerView.visibility(false)
                    } else {
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        carousellNewsViewModel.dataLoading.observe(this, Observer {
            progressBar.visibility(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            R.id.action_recent -> {
                carousellNewsViewModel.getCarousellNews(SortType.RECENT)
            }

            R.id.action_popular -> {
                carousellNewsViewModel.getCarousellNews(SortType.POPULAR)
            }

            R.id.action_reset -> {
                carousellNewsViewModel.getCarousellNews(SortType.RESET)
            }

            R.id.action_reload -> {
                carousellNewsViewModel.getCarousellNews(SortType.RESET)
            }
        }
        
        return super.onOptionsItemSelected(item)
    }
}