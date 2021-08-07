package com.project.app.helpers

import android.app.Activity
import android.app.AlertDialog
import com.project.app.R

class LoadingDialog (private var activity: Activity) {

    private lateinit var dialog: AlertDialog

    fun showAlertDialog(){
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.custom_dialog, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)
        dialog.show()
    }

    fun hideAlertDialog(){
        if (this::dialog.isInitialized) {
            dialog.dismiss()
        }
    }
}