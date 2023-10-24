package com.maad.githubrepos

import android.content.Context
import android.content.res.Resources
import android.icu.util.Calendar
import android.util.Log
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class RepositoryModel(
    @SerializedName("created_at")
    val creationDate: String = ""
)

fun formatDate(context: Context, date: String): String {
    val datetime = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
    val zone = ZoneId.of("UTC")

    val year = datetime.year
    val month = datetime.monthValue
    val day = datetime.dayOfMonth

    val calendar = Calendar.getInstance()
    val yearsDiff = calendar.get(Calendar.YEAR) - year
    val monthsDiff = calendar.get(Calendar.MONTH) - month + 1 //Months are Zero based
    val daysDiff = calendar.get(Calendar.DAY_OF_MONTH) - day
    Log.d("trace", "$yearsDiff")
    Log.d("trace", "$monthsDiff")
    Log.d("trace", "$daysDiff")
    return if (yearsDiff == 0 && monthsDiff == 0 && daysDiff == 0)
        context.getString(R.string.created_today)
    else if (yearsDiff == 0 && monthsDiff == 0)
        context.getString(R.string.day_s_ago, daysDiff.toString())
    else if (yearsDiff == 0 && monthsDiff <= 6)
        context.getString(R.string.month_s_ago, monthsDiff.toString())
    else
        datetime.atZone(zone).format(DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy"))
}