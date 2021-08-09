package com.app.carousellnews.ui.newslisting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.carousellnews.R
import com.app.carousellnews.pojo.Carousell
import com.app.carousellnews.utilities.Utility
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.news_list_item_layout.view.*
import java.util.*

private const val TAG = "NewsAdapter"

class NewsAdapter(var list: MutableList<Carousell>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(private val containerView: View) :
        RecyclerView.ViewHolder(containerView) {

        fun bind(carousell: Carousell) {
            itemView.textViewTitle.text = carousell.title
            itemView.textViewDescription.text = carousell.description
            itemView.textViewTime.text = Utility.getTimeAgo(
                carousell.time_created * 1000,
                Calendar.getInstance().timeInMillis
            )
            Glide.with(itemView.context)
                .load(carousell.banner_url)
                .into(itemView.imageViewBanner)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_list_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(dataList: MutableList<Carousell>) {
        list.clear()
        list.addAll(dataList)
        notifyDataSetChanged()
    }
}