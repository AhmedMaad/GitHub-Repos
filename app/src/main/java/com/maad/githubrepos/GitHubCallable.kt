package com.maad.githubrepos

import com.maad.githubrepos.Constants.REPOSITORIES_ENDPOINT
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface GitHubCallable {

    @Headers("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")
    @GET(REPOSITORIES_ENDPOINT)
    fun getPublicRepositories(): Call<ArrayList<GitHubModel>>

    @GET("/repos/{user}/{repoName}")
    fun getUserRepositories(
        @Path("user") owner: String,
        @Path("repoName") repoName: String
    ): Call<RepositoryModel>

}