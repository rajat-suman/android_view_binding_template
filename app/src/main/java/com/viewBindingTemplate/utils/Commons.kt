package com.viewBindingTemplate.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.hbb20.CountryCodePicker
import com.viewBindingTemplate.R
import com.viewBindingTemplate.controller.Controller
import com.viewBindingTemplate.customclasses.coroutines.mainThread
import com.viewBindingTemplate.ui.activity.mainActivity.MainActivity
import com.viewBindingTemplate.utils.ConstantsValue.CURRENCY_SYMBOL
import okhttp3.internal.toHexString

/**
 * Read Text From EditText
 * */
fun EditText.readText() = text.trim().toString()

/**
 * Show Toast Message
 * Input is String
 * */
fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    mainThread {
        Controller.context?.get()?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }
}

fun Window.adjustResize() {
    // To set adjustResize behavior
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        setDecorFitsSystemWindows(true)
    } else {
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }
}

fun ImageView.loadImage(
    url: String?,
    @DrawableRes placeHolder: Int? = null,
    showImageAlert: Boolean = false,
    initials: String? = null,
    circleCrop: Boolean = false,
) {

    val initialsImage: Bitmap? =
        initials?.takeUnless { it.isBlank() || placeHolder == R.drawable.corner_round_5sdp }?.run {
            val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)

            val paint = Paint()
            paint.color = ContextCompat.getColor(context, R.color.app_color)
            paint.style = Paint.Style.FILL
            canvas.drawPaint(paint)

            val textPaint = Paint()
            textPaint.color = Color.WHITE
            textPaint.textAlign = Paint.Align.CENTER
            textPaint.textSize = 16 * resources.displayMetrics.density

            val xPos: Float = (canvas.width / 2).toFloat()
            canvas.drawText(
                this,
                xPos,
                (canvas.height / 2 - (textPaint.descent() + textPaint.ascent()) / 2),
                textPaint
            )
            bitmap
        }

    var request = Glide.with(this).load(url ?: initialsImage ?: placeHolder ?: return)

    if (placeHolder != null) {
        request = request.placeholder(placeHolder)
    }
    if (circleCrop) {
        request = request.circleCrop()
    }

    request.into(this)

    if (!url.isNullOrBlank() && showImageAlert) setOnClickListener {

    }
}


fun toggleCardVisibility(
    view: TextView,
    constraint: ConstraintLayout,
    arrowDownDrawable: Drawable?,
    topArrowDrawable: Drawable?,
) {
    val flag = !constraint.isVisible
    view.apply {
        if (flag) {
            constraint.visible()
            setCompoundDrawablesWithIntrinsicBounds(null, null, arrowDownDrawable, null)
        } else {
            constraint.gone()
            setCompoundDrawablesWithIntrinsicBounds(null, null, topArrowDrawable, null)
        }
    }
}

fun Fragment.getColor(@ColorRes colorId: Int) =
    this.requireContext().let { ContextCompat.getColor(it, colorId) }

fun Context.mailMe(mail: String) {
    try {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:$mail") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, mail)
        intent.putExtra(Intent.EXTRA_SUBJECT, "Contact")
        ContextCompat.startActivity(
            this, Intent.createChooser(intent, getString(R.string.email)), null
        )
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
    }
}

fun Context.openDialPad(phone: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
            putExtra(Intent.EXTRA_PHONE_NUMBER, phone)
        }
        ContextCompat.startActivity(
            this, Intent.createChooser(intent, getString(R.string.dial_call)), null
        )
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
    }
}

fun Any?.toJson() = Gson().toJson(this)
inline fun <reified T> String?.convertToClass() = Gson().fromJson(this, T::class.java)

fun Fragment.getColorStateList(@ColorRes colorId: Int) =
    this.requireContext().let { ContextCompat.getColorStateList(it, colorId) }

fun Fragment.getDrawable(@DrawableRes drawableId: Int) =
    this.requireContext().let { ContextCompat.getDrawable(it, drawableId) }

fun parseToColor(colorValue: Int, text: String) =
    "\n<font color=\"#${colorValue.toHexString().substring(2)}\">$text</font>"

fun String.toHtml() = HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)

/**
 * Show Toast Message
 * Input is String Id
 * */
fun showToast(@StringRes stringId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Controller.context?.get()?.getString(stringId)?.let { showToast(it, duration) }
}

fun hideSoftKeyBoard() = try {
    (Controller.context?.get())?.apply {
        this as MainActivity
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(
                currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
} catch (e: Exception) {
    e.printStackTrace()
}

fun Fragment.showGuestDialog() {
    requireActivity().openDialog(
        title = getString(R.string.login_first),
        message = getString(R.string.to_access_this_feature_you_have_to_login_first),
        positiveString = getString(R.string.ok),
        negativeString = getString(R.string.cancel)
    ) {
        findNavController().popBackStack(R.id.treatzGraph, true)
        findNavController().navigate(R.id.splash)
    }
}

fun Float?.formatUpto(digit: Int) = String.format("%.2f", (this ?: 0f)).replace(".00", "")
fun Double?.formatUpto(digit: Int) = String.format("%.2f", (this ?: 0.0)).replace(".00", "")

fun String?.attachPriceTag(toStart: Boolean = true) =
    if (toStart) "$CURRENCY_SYMBOL${this ?: ""}" else "${this ?: ""}$CURRENCY_SYMBOL"


fun String?.makeCap() = this.orEmpty().replaceFirstChar { it.uppercase() }

fun String?.initials() = if (this.isNullOrEmpty()) null else this[0].toString().makeCap()

fun CountryCodePicker.setPicker(countryCode: String) {
    try {
        if (countryCode.contains("+")) {
            val temp = countryCode.replace("+", "")
            setCountryForPhoneCode(temp.toInt())
        } else {
            setCountryForPhoneCode(countryCode.toInt())
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
