package com.maad.githubrepos.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.maad.githubrepos.data.GitHubModel
import com.maad.githubrepos.R
import com.maad.githubrepos.data.formatDate
import com.maad.githubrepos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.gitRepositories.observe(this) {
            Log.d("trace", "getting list")
            if (it.isEmpty())
                showSnackBar(R.string.check_connection, R.string.ok)
            else
                showList(it)
            binding.progress.isVisible = false
        }

        viewModel.date.observe(this) {
            if (it != null) {
                val dialog = DateDialog(formatDate(this, it))
                dialog.show(supportFragmentManager, null)
            } else
                showSnackBar(R.string.no_date_found, R.string.ok)
        }

    }

    private fun showList(responseBody: List<GitHubModel>?) {
        val adapter = ListAdapter(responseBody!!) { position ->
            viewModel.getCreationDate(
                responseBody[position].owner.ownerName,
                responseBody[position].repoName
            )
        }
        binding.reposRv.setHasFixedSize(true)
        binding.reposRv.adapter = adapter
    }

    private fun showSnackBar(@StringRes message: Int, @StringRes action: Int) {
        Snackbar
            .make(
                binding.root, message,
                BaseTransientBottomBar.LENGTH_INDEFINITE
            )
            .setAction(action) {}
            .show()
    }

}