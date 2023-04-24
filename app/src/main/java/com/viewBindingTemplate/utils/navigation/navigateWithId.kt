package com.viewBindingTemplate.utils.navigation

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import androidx.navigation.findNavController

/**
 * Navigate using Nav Directions
 * */
fun View.navigateWithAction(action: NavDirections) = try {
    this.findNavController().navigate(action)
} catch (e: Exception) {
    e.printStackTrace()
}

/**
 * Navigate Back using Back Press Dispatcher
 * */
fun Fragment.goBack() = try {
    requireActivity().onBackPressedDispatcher.onBackPressed()
} catch (e: Exception) {
    e.printStackTrace()
}

/**
 * Navigate using Nav Directions with extras
 * */
fun View.navigateWithAction(action: NavDirections,extras : Navigator.Extras ) = try {
    this.findNavController().navigate(action,extras)
} catch (e: Exception) {
    e.printStackTrace()
}
