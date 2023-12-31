package com.maad.githubrepos.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.maad.githubrepos.database.DBHelper
import com.maad.githubrepos.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val _date = MutableLiveData<String?>()
    val date: LiveData<String?>
        get() = _date

    private val repository = Repository(DBHelper.getDBInstance(app))

    val gitRepositories = repository.cachedRepos

    private val _hasError = MutableLiveData<Boolean>()
    val hasError: LiveData<Boolean>
        get() = _hasError

    val _hasDateError = MutableLiveData<Boolean>()
    val hasDateError: LiveData<Boolean>
        get() = _hasDateError

    init {
        getRepositories()
    }

    private fun getRepositories() {
        viewModelScope.launch {
            try {
                repository.refreshRepositories()
            } catch (e: Exception) {
                _hasError.value = true
                //Log.d("trace", "Fetching Repository Error: $e")
            }
        }
    }

    fun getCreationDate(ownerName: String?, repoName: String?) {
        viewModelScope.launch {
            try {
                _date.value = repository.refreshCreationDate(ownerName, repoName)
            } catch (e: Exception) {
                _hasDateError.value = true
                //Log.d("trace", "Fetching Repository Error: $e")
            }
        }
    }

    fun showedSnackBar() {
        _hasError.value = false
    }

    fun showedDateSnackBar(){
        _hasDateError.value = false
    }

    fun showedDateDialog() {
        _date.value = null
    }

}