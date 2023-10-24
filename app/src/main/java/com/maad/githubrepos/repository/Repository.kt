package com.maad.githubrepos.repository

import android.util.Log
import com.maad.githubrepos.api.GitHubAPI
import com.maad.githubrepos.data.GitHubModel
import com.maad.githubrepos.data.RepositoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {

    private var allRepos = arrayListOf<GitHubModel>()
    private var repoModel = RepositoryModel()

    suspend fun getRepositories(): ArrayList<GitHubModel> {
        //The I/O dispatcher designed to offload blocking I/O tasks to a shared pool of threads
        //The IO dispatcher is optimized for IO work like reading from the network or disk,
        //while the Default dispatcher is optimized for CPU intensive tasks.
        withContext(Dispatchers.IO) {
            allRepos = GitHubAPI.callable.getPublicRepositories()
        }
        return allRepos
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