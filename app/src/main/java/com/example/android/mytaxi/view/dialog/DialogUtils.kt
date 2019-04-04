@file:Suppress("DEPRECATION")

package com.example.android.mytaxi.view.dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface.OnClickListener
import com.example.android.mytaxi.R

object DialogUtils {
    fun showProgressDialog(context: Context, message: String): ProgressDialog {
        return showProgressDialog(context, message, false)
    }

    private fun showProgressDialog(context: Context, message: String, cancelable: Boolean): ProgressDialog {
        val dialog = ProgressDialog(context)
        dialog.setMessage(message)
        dialog.isIndeterminate = true
        dialog.setCancelable(cancelable)
        dialog.show()
        return dialog
    }

    @JvmOverloads
    fun showSimpleAlert(context: Context, titleResource: Int, messageResource: Int, listener: OnClickListener? = null) {
        val dialogTitle = if (titleResource > 0) context.getString(titleResource) else null
        val dialogMessage = if (messageResource > 0) context.getString(messageResource) else null
        showSimpleAlert(context, dialogTitle, dialogMessage, listener)
    }

    private fun showSimpleAlert(context: Context, title: String?, message: String?, listener: OnClickListener?) {
        if (context is Activity && context.isFinishing) {
            return
        }
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton(R.string.dialog_ok, listener)
        builder.show()
    }

    fun showNoConnectionDialog(activity: Activity) {
        val listener = OnClickListener { dialog, _ -> dialog.cancel() }
        showSimpleAlert(activity, R.string.network_error_dialog_title, R.string.network_error_dialog_message, listener)
    }
}