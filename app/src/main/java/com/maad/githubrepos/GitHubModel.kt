package com.maad.githubrepos

import com.google.gson.annotations.SerializedName

class GitHubModel {

    @SerializedName("name")
    val repoName = ""

}

class Owner {

    @SerializedName("login")
    val ownerName = ""

    @SerializedName("avatar_url")
    val avatarUrl = ""

    //https://api.github.com/users/{ownerName}/repos
    //Used to get the creation date for a repository
    //@SerializedName("repos_url")
    //val reposUrl = ""

}