package com.viewBindingTemplate.utils

import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.widget.EditText
import android.widget.Toast
import com.viewBindingTemplate.R
import org.json.JSONObject


object DataValidations {

    private val shake by lazy {
        TranslateAnimation(0f, 10f, 0f, 0f).apply {
            duration = 500
            interpolator = CycleInterpolator(5F)
        }
    }

    fun isEmailValid(
        email: EditText,
        callBack: JSONObject.() -> Unit,
    ) {
        when {
            email.readText().isEmpty() -> {
                email.startAnimation(shake)
                showToast(email.context.getString(R.string.please_enter_email))
            }
            !email.readText().isValidEmail() -> {
                email.startAnimation(shake)
                showToast(email.context.getString(R.string.please_enter_valid_email))
            }
            else -> callBack.invoke(JSONObject().apply {

            })
        }
    }

    fun isResetPasswordValid(
        password: EditText,
        confirmPassword: EditText,
        callBack: JSONObject.() -> Unit,
    ) {
        when {

            password.readText().isEmpty() -> {
                password.startAnimation(shake)
                showToast(password.getString(R.string.please_enter_password))
            }

            password.readText().isValidPassword() -> {
                password.startAnimation(shake)
                showToast(
                    password.getString(R.string.password_validation_message),
                    Toast.LENGTH_LONG
                )
            }

            confirmPassword.readText().isEmpty() -> {
                confirmPassword.startAnimation(shake)
                showToast(confirmPassword.getString(R.string.please_enter_confirm_password))
            }
            password.readText() isMatch confirmPassword.readText() -> {
                confirmPassword.startAnimation(shake)
                showToast(confirmPassword.getString(R.string.password_and_confirm_password_dosent_match))
            }

            else -> callBack.invoke(JSONObject().apply {

            })
        }
    }

}