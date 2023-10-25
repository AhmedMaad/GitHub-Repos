package com.maad.githubrepos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.maad.githubrepos.data.GitHubModel
import com.maad.githubrepos.data.Owner
import com.maad.githubrepos.data.RepositoryModel

@Database(entities = [GitHubModel::class], version = 1)
abstract class DBHelper: RoomDatabase() {

    abstract fun ownerDao(): OwnerDao
    companion object {
        //Ensures that the value of the variable is always up-to-date,
        //and any changes to the variable should be immediately visible to all threads.
        @Volatile
        private var INSTANCE: DBHelper? = null

        fun getDBInstance(context: Context): DBHelper {
            //Lock the current object to prevent race conditions.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context, DBHelper::class.java, "MyRepos").build()
                INSTANCE = instance

                instance
            }
        }

    }

}