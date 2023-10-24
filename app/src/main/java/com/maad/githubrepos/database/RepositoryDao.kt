package com.maad.githubrepos.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.maad.githubrepos.data.RepositoryModel

@Dao
interface RepositoryDao {

    @Insert
    suspend fun insertRepoDate(repo: RepositoryModel)

    @Query("SELECT created_at FROM repository WHERE repo_id = :id")
    suspend fun getRepoDate(id: Int): String

}