package com.maad.githubrepos.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.maad.githubrepos.data.GitHubModel
import com.maad.githubrepos.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val app: Application) : AndroidViewModel(app) {

    private val _gitRepositories = MutableLiveData<ArrayList<GitHubModel>>()
    val gitRepositories: LiveData<ArrayList<GitHubModel>>
        get() = _gitRepositories

    private val _date = MutableLiveData<String?>()
    val date: LiveData<String?>
        get() = _date

    private val repository = Repository()

    init {
        getRepositories()
    }

    private fun getRepositories(){
        viewModelScope.launch {
            try {
               _gitRepositories.value = repository.getRepositories()
                Log.d("trace", "Git Repos value: ${_gitRepositories.value}")
            }
            catch (e: Exception){
                Log.d("trace", "Fetching Repository Error: $e")
            }
        }
    }

    fun getCreationDate(ownerName: String?, repoName: String?){
        viewModelScope.launch {
            try {
                val date: String? = repository.getCreationDate(ownerName, repoName)
                _date.value = date
            }
            catch (e:Exception){
                Log.d("trace", "Fetching Date Error: $e")
            }
        }
    }

}