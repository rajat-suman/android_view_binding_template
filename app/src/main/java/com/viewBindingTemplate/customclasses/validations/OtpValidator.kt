package com.viewBindingTemplate.customclasses.validations

import android.view.View

class OtpValidator(
    val view: View,
    private val editable: String,
    private val otpMaxLength: Int,
    val message: String,
) : Validator {

    override fun isValid(): Boolean {
        return editable.length == otpMaxLength
    }

    override fun view() = view

    override fun message(): String {
        return message
    }
}