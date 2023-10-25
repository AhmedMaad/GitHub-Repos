package com.maad.githubrepos.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maad.githubrepos.data.GitHubModel

@Dao
interface OwnerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ownersData: ArrayList<GitHubModel>)

    @Query("SELECT * FROM github")
    fun getAll(): LiveData<List<GitHubModel>>

}