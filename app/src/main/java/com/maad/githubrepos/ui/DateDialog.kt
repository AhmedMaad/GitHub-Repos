package com.maad.githubrepos.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.DialogFragment
import com.maad.githubrepos.R

class DateDialog(private val message: String, private val viewModel: MainViewModel) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireActivity())
        builder
            .setTitle(getString(R.string.date))
            .setMessage(message)
            .setNegativeButton(getString(R.string.dismiss)) { dialog, _ ->
                dialog.dismiss()
                viewModel.showedDateDialog()
            }

        return builder.create()
    }

}