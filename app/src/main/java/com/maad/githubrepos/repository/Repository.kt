package com.maad.githubrepos.repository

import android.util.Log
import com.maad.githubrepos.api.GitHubAPI
import com.maad.githubrepos.data.GitHubModel
import com.maad.githubrepos.data.RepositoryModel
import com.maad.githubrepos.database.DBHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val db: DBHelper) {

    var cachedRepos = db.ownerDao().getAll()

    private var allRepos = arrayListOf<GitHubModel>()
    private var repoModel = RepositoryModel()

    suspend fun refreshRepositories() {
        //The I/O dispatcher designed to offload blocking I/O tasks to a shared pool of threads
        //The IO dispatcher is optimized for IO work like reading from the network or disk,
        //while the Default dispatcher is optimized for CPU intensive tasks.
        withContext(Dispatchers.IO) {
            allRepos = GitHubAPI.callable.getPublicRepositories()
            db.ownerDao().insertAll(allRepos)
            Log.d("trace", "repos saved successfully")
        }
    }

    suspend fun getCreationDate(ownerName: String?, repoName: String?): String? {
        withContext(Dispatchers.IO){
            repoModel = GitHubAPI.callable.getRepositoryData(ownerName!!, repoName!!)
        }
        val date = repoModel.creationDate
        Log.d("trace", "Date value: $date")
       return date
    }

}