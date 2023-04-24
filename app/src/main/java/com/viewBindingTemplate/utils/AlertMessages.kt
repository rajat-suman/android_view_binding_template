package com.viewBindingTemplate.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.viewBindingTemplate.R
import com.viewBindingTemplate.customclasses.datastore.DataStoreUtil
import com.viewBindingTemplate.customclasses.datastore.DatastoreKeys
import com.viewBindingTemplate.databinding.ProgressLayoutBinding
import com.viewBindingTemplate.ui.activity.mainActivity.MainActivity

/**
 * Show Alert Dialog
 * */
fun Activity.openDialog(
    title: String,
    message: String,
    positiveString: String = "",
    negativeString: String = "",
    callBack: () -> Unit,
) {
    MaterialAlertDialogBuilder(this).apply {
        setCancelable(false)
        setFinishOnTouchOutside(false)
        setTitle(title)
        setMessage(message)
        setPositiveButton(positiveString) { dialog, _ ->
            dialog.dismiss()
            callBack()
        }
        setNegativeButton(negativeString) { dialog, _ ->
            dialog.dismiss()
        }
        create()
        show()
    }
}


var isSessionPopupShowing = false
fun sessionExpired(context: Context, dataStore: DataStoreUtil) {
    if (!isSessionPopupShowing) {
        isSessionPopupShowing = true
        MaterialAlertDialogBuilder(context).apply {
            setCancelable(false)
            setTitle(context.getString(R.string.session_expired))
            setMessage(context.getString(R.string.please_login_again))
            setPositiveButton(context.getString(R.string.ok)) { dialog, _ ->
                dataStore.removeKey(DatastoreKeys.AUTH_TOKEN) {}
                dataStore.removeKey(DatastoreKeys.REMEMBER) {}
                dataStore.removeKey(DatastoreKeys.REFRESH_TOKEN) {}
                if (context is MainActivity) {
                    with(
                        Navigation.findNavController(
                            context, R.id.fragmentContainerView
                        )
                    ) {
                        popBackStack(R.id.treatzGraph, true)
                        navigate(R.id.splash)
                    }
                }
                isSessionPopupShowing = false
                dialog.dismiss()
            }
            create()
            show()
        }
    }
}

/**
 * Show Error Handler
 * */
fun Activity.showExitSnackBar() {
    try {
        Snackbar.make(
            findViewById(android.R.id.content),
            getString(R.string.are_you_sure_want_to_exit),
            Snackbar.LENGTH_LONG
        ).apply {
            setBackgroundTint(ContextCompat.getColor(this@showExitSnackBar, R.color.app_color))
            animationMode = Snackbar.ANIMATION_MODE_SLIDE
            setTextColor(ContextCompat.getColor(this@showExitSnackBar, R.color.white))
            setActionTextColor(ContextCompat.getColor(this@showExitSnackBar, R.color.white))
            view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines = 5
            setAction(R.string.yes) { finishAffinity() }
            show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun View.showSnackBar(title: String) {
    try {
        Snackbar.make(
            this, title, Snackbar.LENGTH_LONG
        ).apply {
            setBackgroundTint(ContextCompat.getColor(context, R.color.app_color))
            animationMode = Snackbar.ANIMATION_MODE_SLIDE
            setTextColor(ContextCompat.getColor(context, R.color.white))
            setActionTextColor(ContextCompat.getColor(context, R.color.white))
            view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines = 5
            show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


/**
 * Generic Bottom Sheet
 * */
fun <T> Context.showBottomAlert(
    viewBinding: ViewBinding,
    callBack: (binding: T, dialog: BottomSheetDialog) -> Unit,
) {
    BottomSheetDialog(this).apply {
        setContentView(viewBinding.root)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        show()
        callBack(viewBinding as T, this)
    }
}

//Loader
private var customDialog: AlertDialog? = null
fun Context.showProgress() {
    hideProgress()
    val customAlertBuilder = AlertDialog.Builder(this)
    val customAlertView = ProgressLayoutBinding.inflate(LayoutInflater.from(this), null, false)
    customAlertBuilder.setView(customAlertView.root)
    customAlertBuilder.setCancelable(false)
    customDialog = customAlertBuilder.create()

    customDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    customDialog?.show()
}

fun hideProgress() {
    try {
        if (customDialog != null && customDialog?.isShowing!!) {
            customDialog?.dismiss()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}