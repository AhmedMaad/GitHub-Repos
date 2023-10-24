package com.maad.githubrepos

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DateDialog(private val message: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireActivity())
        builder
            .setTitle(getString(R.string.date))
            .setMessage(message)
            .setNegativeButton(getString(R.string.dismiss)) { dialog, _ ->
                dialog.dismiss()
            }

        return builder.create()
    }

}