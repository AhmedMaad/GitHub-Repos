package com.maad.githubrepos.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maad.githubrepos.R
import com.maad.githubrepos.api.GitHubAPI
import com.maad.githubrepos.data.GitHubModel
import com.maad.githubrepos.data.RepositoryModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

class MainActivityViewModel(private val app: Application) : AndroidViewModel(app) {

    private val _gitRepositories = MutableLiveData<ArrayList<GitHubModel>>()
    val gitRepositories: LiveData<ArrayList<GitHubModel>>
        get() = _gitRepositories

    private val _date = MutableLiveData<String>()
    val date: LiveData<String>
        get() = _date

    init {
        getRepositories()
    }

    private fun getRepositories() {

        GitHubAPI.callable.getPublicRepositories().enqueue(object :
            Callback<ArrayList<GitHubModel>> {
            override fun onResponse(
                call: Call<ArrayList<GitHubModel>>,
                response: Response<ArrayList<GitHubModel>>
            ) {
                if (response.isSuccessful)
                    _gitRepositories.value = response.body()

                else
                    Log.d("trace", "Error getting data: ${response.errorBody()}")
            }

            override fun onFailure(call: Call<ArrayList<GitHubModel>>, t: Throwable) {
                Log.d("trace", "Error: ${t.message}")
            }
        })

    }

    fun getCreationDate(ownerName: String?, repoName: String?) {
        GitHubAPI.callable.getUserRepositories(ownerName!!, repoName!!)
            .enqueue(object : Callback<RepositoryModel> {
                override fun onResponse(
                    call: Call<RepositoryModel>,
                    response: Response<RepositoryModel>
                ) {
                    val date = response.body()?.creationDate
                    Log.d("trace", "Date value: $date")
                    if (date != null) {
                        _date.value = date
                    } else
                        _date.value = ""
                }

                override fun onFailure(call: Call<RepositoryModel>, t: Throwable) {
                    Log.d("trace", "Error: ${t.message}")
                }
            })
    }


}