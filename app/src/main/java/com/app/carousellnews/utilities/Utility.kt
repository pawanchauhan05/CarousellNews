package com.app.carousellnews.utilities

import android.text.format.DateUtils

object Utility {

    fun getTimeAgo(mReferenceTime: Long, now : Long): String {
        val diff = now - mReferenceTime
        return if (diff < DateUtils.WEEK_IN_MILLIS) {
            if (diff <= 1000) "just now" else DateUtils.getRelativeTimeSpanString(
                mReferenceTime, now, DateUtils.MINUTE_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE
            ).toString()
        } else if (diff <= 4 * DateUtils.WEEK_IN_MILLIS) {
            val week = (diff / DateUtils.WEEK_IN_MILLIS).toInt()
            if (week > 1) "$week weeks ago" else "$week week ago"
        } else if (diff < DateUtils.YEAR_IN_MILLIS) {
            val month = (diff / (4 * DateUtils.WEEK_IN_MILLIS)).toInt()
            if (month > 1) "$month months ago" else "$month month ago"
        } else {
            val year = (diff / DateUtils.YEAR_IN_MILLIS).toInt()
            if (year > 1) "$year years ago" else "$year year ago"
        }
    }
}