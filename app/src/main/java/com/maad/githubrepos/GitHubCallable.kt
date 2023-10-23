package com.maad.githubrepos

import com.maad.githubrepos.Constants.REPOSITORIES_ENDPOINT
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubCallable {

    @GET(REPOSITORIES_ENDPOINT)
    fun getPublicRepositories(): Call<ArrayList<GitHubModel>>

    @GET("/users/{user}/repos")
    fun getUserRepositories(@Path("user") owner: String): Call<List<RepositoryModel>>

}