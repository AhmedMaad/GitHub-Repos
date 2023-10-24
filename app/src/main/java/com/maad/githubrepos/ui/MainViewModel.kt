package com.maad.githubrepos.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.maad.githubrepos.data.GitHubModel
import com.maad.githubrepos.database.DBHelper
import com.maad.githubrepos.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val app: Application) : AndroidViewModel(app) {

    private val _date = MutableLiveData<String?>()
    val date: LiveData<String?>
        get() = _date

    private val repository = Repository(DBHelper.getDBInstance(app))

    val gitRepositories = repository.cachedRepos

    /*private val _hasRepoError = MutableLiveData<Boolean>()
    val hasRepoError: LiveData<Boolean>
        get() = _hasRepoError*/

    /*private val _hasDateError = MutableLiveData<Boolean>()
    val hasDateError: LiveData<Boolean>
        get() = _hasDateError*/

    init {
        getRepositories()
    }

    private fun getRepositories() {
        viewModelScope.launch {
            try {
                repository.refreshRepositories()
            } catch (e: Exception) {
                //_hasRepoError.value = true
                Log.d("trace", "Fetching Repository Error: $e")
            }
        }
    }

    fun getCreationDate(ownerName: String?, repoName: String?) {
        viewModelScope.launch {
            try {
                val date: String? = repository.getCreationDate(ownerName, repoName)
                _date.value = date
            } catch (e: Exception) {
                //_hasDateError.value = true
                Log.d("trace", "Fetching Date Error: $e")
            }
        }
    }

}