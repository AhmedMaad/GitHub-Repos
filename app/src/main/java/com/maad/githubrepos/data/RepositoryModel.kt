package com.maad.githubrepos.data

import android.content.Context
import android.util.Log
import com.google.gson.annotations.SerializedName
import com.maad.githubrepos.R
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

data class RepositoryModel(
    @SerializedName("created_at")
    val creationDate: String = ""
)

fun formatDate(context: Context, date: String): String {

    val datetime = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
    val zone = ZoneId.of("UTC")
    Log.d("trace", "Formatted Date: $datetime")
    val year = datetime.year
    val month = datetime.monthValue
    val day = datetime.dayOfMonth

    val calendar = Calendar.getInstance()
    val yearsDiff = calendar.get(Calendar.YEAR) - year
    val monthsDiff = calendar.get(Calendar.MONTH) - month + 1 //Months are Zero based
    val daysDiff = calendar.get(Calendar.DAY_OF_MONTH) - day
    Log.d("trace", "yearsDiff: $yearsDiff")
    Log.d("trace", "monthsDiff: $monthsDiff")
    Log.d("trace", "daysDiff: $daysDiff")
    return if (yearsDiff == 0 && monthsDiff == 0 && daysDiff == 0)
        context.getString(R.string.created_today)
    else if (yearsDiff == 0 && monthsDiff == 0)
        context.getString(R.string.day_s_ago, daysDiff.toString())
    else if (yearsDiff == 0 && monthsDiff <= 6)
        context.getString(R.string.month_s_ago, monthsDiff.toString())
    else
        datetime.atZone(zone).format(DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy"))
}