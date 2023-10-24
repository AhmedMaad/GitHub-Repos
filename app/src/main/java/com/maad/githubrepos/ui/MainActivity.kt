package com.maad.githubrepos.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.maad.githubrepos.api.GitHubCallable
import com.maad.githubrepos.data.GitHubModel
import com.maad.githubrepos.R
import com.maad.githubrepos.data.formatDate
import com.maad.githubrepos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.gitRepositories.observe(this) {
            showList(it)
        }

        viewModel.date.observe(this) {
            if (it.isNotEmpty()) {
                val dialog = DateDialog(formatDate(this, it))
                dialog.show(supportFragmentManager, null)
            } else {
                Snackbar
                    .make(
                        binding.root, getString(R.string.no_date_found),
                        BaseTransientBottomBar.LENGTH_INDEFINITE
                    )
                    .setAction(getString(R.string.ok)) {}
                    .show()
            }
        }

    }

    private fun showList(responseBody: ArrayList<GitHubModel>?) {
        binding.progress.isVisible = false
        val adapter = ListAdapter(responseBody!!) { position ->
            viewModel.getCreationDate(
                responseBody[position].owner.ownerName,
                responseBody[position].repoName
            )
        }
        binding.reposRv.setHasFixedSize(true)
        binding.reposRv.adapter = adapter
    }

}