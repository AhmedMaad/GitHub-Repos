package com.maad.githubrepos.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.maad.githubrepos.Constants.AVATAR_URL
import com.maad.githubrepos.Constants.GITHUB
import com.maad.githubrepos.Constants.ID
import com.maad.githubrepos.Constants.LOGIN
import com.maad.githubrepos.Constants.NAME
import com.maad.githubrepos.Constants.OWNER_ID
import com.maad.githubrepos.Constants.OWNER_NAME
import com.maad.githubrepos.Constants.REPO_NAME

@Entity(GITHUB)
data class GitHubModel(

    @PrimaryKey
    val id: Int = 0,

    @ColumnInfo(REPO_NAME)
    @SerializedName(NAME)
    val repoName: String = "",

    @Embedded
    val owner: Owner = Owner()
)

data class Owner(
    @ColumnInfo(OWNER_ID)
    @SerializedName(ID)
    val ownerId: Int = 0,

    @ColumnInfo(OWNER_NAME)
    @SerializedName(LOGIN)
    val ownerName: String = "",

    @ColumnInfo(AVATAR_URL)
    @SerializedName(AVATAR_URL)
    val avatarUrl: String = ""
)
