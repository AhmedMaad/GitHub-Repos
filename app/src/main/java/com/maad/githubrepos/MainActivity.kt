package com.maad.githubrepos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.maad.githubrepos.Constants.BASE_URL
import com.maad.githubrepos.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofit: Retrofit
    private lateinit var callable: GitHubCallable
    private val dates = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        callable = retrofit.create(GitHubCallable::class.java)

        getRepositories()

    }

    private fun getRepositories() {

        callable.getPublicRepositories().enqueue(object : Callback<ArrayList<GitHubModel>> {
            override fun onResponse(
                call: Call<ArrayList<GitHubModel>>,
                response: Response<ArrayList<GitHubModel>>
            ) {
                binding.progress.isVisible = false
                val allRepos: ArrayList<GitHubModel>? = response.body()
                val adapter = ListAdapter(allRepos!!){
                    Log.d("trace", "Date should appear now")
                }
                binding.reposRv.setHasFixedSize(true)
                binding.reposRv.adapter = adapter

                //Giving API request limit, so will be replaced with getting a date ""
               /* if (allRepos != null)
                    for (element in allRepos) {
                        Log.d("trace", "Data: $element")
                        getCreationDate(element.owner.ownerName, element.repoName)
                    }
                else
                    Log.d("trace", "Null repos")*/

            }

            override fun onFailure(call: Call<ArrayList<GitHubModel>>, t: Throwable) {
                binding.progress.isVisible = false
                Log.d("trace", "Error: ${t.message}")
            }
        })

    }

    /*private fun getCreationDate(ownerName: String?, repoName: String?) {
        callable.getUserRepositories(ownerName!!, repoName!!)
            .enqueue(object : Callback<RepositoryModel> {
                override fun onResponse(
                    call: Call<RepositoryModel>,
                    response: Response<RepositoryModel>
                ) {
                    val date = response.body()?.creationDate
                    Log.d("trace", "Date: $date")
                    dates.add(date ?: "No date")
                    Log.d("trace", "array list value: ${dates[dates.size - 1]}")
                }

                override fun onFailure(call: Call<RepositoryModel>, t: Throwable) {
                    Log.d("trace", "Error: ${t.message}")
                }
            })
    }*/


}