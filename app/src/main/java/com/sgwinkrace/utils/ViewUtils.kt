package com.sgwinkrace.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.sgwinkrace.R

fun Context.snackBar(view:View,message:String,action:String="OK"){
    val snackBar = Snackbar.make(
        view, message,
        Snackbar.LENGTH_LONG
    ).setAction(action) { view ->

    }
    snackBar.setActionTextColor(getColor(R.color.colorPrimary))

    val snackBarView = snackBar.view

    snackBarView.setBackgroundColor(Color.BLACK)
    snackBar.setTextColor(getColor(R.color.white))



    snackBar.show()
}


fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun <A : Activity> Activity.startNextActivity(activity: Class<A>) {
    Intent(this, activity).also {
        startActivity(it)
    }
}

fun Context.myToast(message:String){
    Toast.makeText(this, message
        , Toast.LENGTH_SHORT).show()
}

fun Context.dialogMsgOk(message:String,title:String="Alert",buttonText:String="OK"){
    MaterialAlertDialogBuilder(this)

        .setTitle("$title")
        .setMessage(message)
        .setPositiveButton(
            "$buttonText"
        ) { dialogInterface, i ->
            dialogInterface.dismiss()
        }

        .show()
}
lateinit var dialog: ProgressDialog


fun showProgressDialog(activity: Activity, message: String = "Please wait...") {
    dialog = ProgressDialog(activity)
    dialog.setMessage(message)
    dialog.setCanceledOnTouchOutside(false)
    dialog.show()
}

fun hideProgressDialog() {
    if (dialog.isShowing) {
        dialog.dismiss()
    }
}