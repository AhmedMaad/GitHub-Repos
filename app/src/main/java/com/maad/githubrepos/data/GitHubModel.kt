package com.maad.githubrepos.data

import com.google.gson.annotations.SerializedName

data class GitHubModel(

    @SerializedName("name")
    val repoName: String = "",

    val owner: Owner = Owner()

    //Used to get the creation date for a repository
    //@SerializedName("url")
    //val repoUrl: String = ""

)

data class Owner(
    @SerializedName("login")
    val ownerName: String = "",

    @SerializedName("avatar_url")
    val avatarUrl: String = ""

)