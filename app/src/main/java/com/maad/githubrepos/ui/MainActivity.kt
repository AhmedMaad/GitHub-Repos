package com.maad.githubrepos.ui

import android.app.AlertDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
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
            //Log.d("trace", "getting list")
            if (it.isNotEmpty()){
                showList(it, savedInstanceState)
                binding.shimmerViewContainer.stopShimmerAnimation()
                binding.shimmerViewContainer.isVisible = false
            }
        }

        viewModel.date.observe(this) {
            // Log.d("trace", "Observing date: $it")
            if (it != null)
                showDialog(formatDate(this, it))
        }

        viewModel.hasError.observe(this) { hasError ->
            if (hasError){
                Snackbar
                    .make(
                        binding.root,
                        R.string.cached_data,
                        BaseTransientBottomBar.LENGTH_INDEFINITE
                    )
                    .setAction(R.string.ok) {}
                    .show()
                viewModel.showedSnackBar()
            }
            else{
                binding.shimmerViewContainer.stopShimmerAnimation()
                binding.shimmerViewContainer.isVisible = false
            }

        }

        viewModel.hasDateError.observe(this) { hasError ->
            if (hasError){
                Snackbar
                    .make(
                        binding.root,
                        R.string.check_connection,
                        BaseTransientBottomBar.LENGTH_INDEFINITE
                    )
                    .setAction(R.string.ok) {}
                    .show()
                viewModel.showedDateSnackBar()
            }
        }

    }

    private fun showDialog(message: String) {
        AlertDialog
            .Builder(this)
            .setTitle(getString(R.string.date))
            .setIcon(R.drawable.date)
            .setMessage(message)
            .setNegativeButton(getString(R.string.dismiss)) { dialog, _ ->
                dialog.dismiss()
                viewModel.showedDateDialog()
            }
            .show()
    }

    private fun showList(responseBody: List<GitHubModel>?, activityState: Bundle?) {
        val adapter = ListAdapter(responseBody!!) { position ->
            viewModel.getCreationDate(
                responseBody[position].owner.ownerName,
                responseBody[position].repoName
            )
        }
        binding.reposRv.setHasFixedSize(true)
        binding.reposRv.adapter = adapter

        if (activityState != null) {
            val managerState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                activityState.getParcelable("recyclerViewState", Parcelable::class.java)
            else
                activityState.getParcelable("recyclerViewState")
            binding.reposRv.layoutManager?.onRestoreInstanceState(managerState)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(
            "recyclerViewState",
            binding.reposRv.layoutManager?.onSaveInstanceState()
        )
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerViewContainer.startShimmerAnimation()
    }

    override fun onPause() {
        super.onPause()
        binding.shimmerViewContainer.stopShimmerAnimation()
    }

}