package com.maad.githubrepos.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.maad.githubrepos.Constants.BASE_URL
import com.maad.githubrepos.api.GitHubCallable
import com.maad.githubrepos.data.GitHubModel
import com.maad.githubrepos.R
import com.maad.githubrepos.data.RepositoryModel
import com.maad.githubrepos.databinding.ActivityMainBinding
import com.maad.githubrepos.data.formatDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofit: Retrofit
    private lateinit var callable: GitHubCallable

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
                if (response.isSuccessful)
                    showList(response.body())
                else
                    Log.d("trace", "Error getting data: ${response.errorBody()}")
            }

            override fun onFailure(call: Call<ArrayList<GitHubModel>>, t: Throwable) {
                binding.progress.isVisible = false
                Log.d("trace", "Error: ${t.message}")
            }
        })

    }

    private fun showList(responseBody: ArrayList<GitHubModel>?) {
        val adapter = ListAdapter(responseBody!!) { position ->
            Log.d("trace", "Date should appear in a dialog")
            getCreationDate(responseBody[position].owner.ownerName, responseBody[position].repoName)
        }
        binding.reposRv.setHasFixedSize(true)
        binding.reposRv.adapter = adapter
    }

    private fun getCreationDate(ownerName: String?, repoName: String?) {
        callable.getUserRepositories(ownerName!!, repoName!!)
            .enqueue(object : Callback<RepositoryModel> {
                override fun onResponse(
                    call: Call<RepositoryModel>,
                    response: Response<RepositoryModel>
                ) {
                    val date: String? = response.body()?.creationDate
                    if (date != null) {
                        val dialog = DateDialog(formatDate(this@MainActivity, date))
                        dialog.show(supportFragmentManager, null)
                    } else
                        Snackbar
                            .make(
                                binding.root, getString(R.string.no_date_found),
                                BaseTransientBottomBar.LENGTH_INDEFINITE
                            )
                            .setAction(getString(R.string.ok)) {}
                            .show()

                }

                override fun onFailure(call: Call<RepositoryModel>, t: Throwable) {
                    Log.d("trace", "Error: ${t.message}")
                }
            })
    }

}