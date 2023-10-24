package com.maad.githubrepos.api

import com.maad.githubrepos.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val retrofit = Retrofit
    .Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object GitHubAPI {
    val callable: GitHubCallable by lazy {
        retrofit.create(GitHubCallable::class.java)
    }
}