package com.viewBindingTemplate.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION
import android.provider.Settings
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.viewBindingTemplate.app.App
import com.viewbinding.R

object PermissionsUtil {

    fun checkPermission(
        context: Context,
        permissionType: PermissionType,
        permissionHandling: PermissionHandling,
    ) {
        val list = ArrayList<String>()
        when (permissionType) {
            PermissionType.CAMERA -> list.add(Manifest.permission.CAMERA)
            PermissionType.NOTIFICATION -> if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                list.add(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                permissionHandling.onPermissionGiven()
                return
            }
        }
        if (list.isEmpty()) {
            return
        }
        checkPermissions(context, list) { permissionGranted, permissionDisabled ->
            when {
                permissionGranted -> permissionHandling.onPermissionGiven()

                permissionDisabled -> {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", context.packageName, null)
                    intent.data = uri
                    context.startActivity(intent)
                    permissionHandling.onPermissionRejected()
                }

                else -> permissionAlert(permissionType) { canRequest ->
                    if (canRequest) checkPermission(context, permissionType, permissionHandling)
                    else permissionHandling.onPermissionRejected()
                }
            }
        }
    }

    /**Check Permission*/
    private fun checkPermissions(
        context: Context,
        list: ArrayList<String>,
        returnValue: (permissionGranted: Boolean, permissionDisabled: Boolean) -> Unit,
    ) = try {
        Dexter.withContext(context).withPermissions(list)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    if (p0?.isAnyPermissionPermanentlyDenied == true) returnValue(false, true)
                    else returnValue(p0?.areAllPermissionsGranted() ?: false, false)
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    p1: PermissionToken?,
                ) {
                    p1?.continuePermissionRequest()
                }
            }).check()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    /**Alert for Permission Necessity*/
    private fun permissionAlert(
        permissionType: PermissionType,
        requestPermission: (Boolean) -> Unit,
    ) {
        val context = App.context?.get()
        val aD = android.app.AlertDialog.Builder(context)
        val title = when (permissionType) {
            PermissionType.CAMERA -> context?.getString(R.string.camera_permission_required)
            PermissionType.NOTIFICATION -> context?.getString(R.string.notification_permission_required)
        }
        aD.setTitle(title)
        aD.setCancelable(false)
        aD.setPositiveButton(context?.getString(R.string.ok)) { dialogInterface, _ ->
            dialogInterface.dismiss()
            requestPermission(true)
        }
        aD.setNegativeButton(context?.getString(R.string.cancel)) { dialogInterface, _ ->
            dialogInterface.cancel()
            requestPermission(false)
        }
        aD.create()
        aD.show()
    }
}

enum class PermissionType {
    CAMERA, NOTIFICATION
}

interface PermissionHandling {
    fun onPermissionGiven()
    fun onPermissionRejected() = Unit
}