package com.maad.githubrepos.api

import com.maad.githubrepos.Constants.REPOSITORIES_ENDPOINT
import com.maad.githubrepos.data.GitHubModel
import com.maad.githubrepos.data.RepositoryModel
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubCallable {

    @GET(REPOSITORIES_ENDPOINT)
    suspend fun getPublicRepositories(): ArrayList<GitHubModel>

    @GET("/repos/{user}/{repoName}")
    suspend fun getRepositoryData(
        @Path("user") owner: String,
        @Path("repoName") repoName: String
    ): RepositoryModel

}