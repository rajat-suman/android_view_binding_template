package com.viewBindingTemplate.customclasses.validations

import android.view.View
import com.viewBindingTemplate.utils.isValidPassword


class PasswordValidator(
    val view: View,
    private val editable: String,
    val message: String,
) : Validator {

    override fun isValid(): Boolean = editable.isValidPassword()

    override fun message(): String {
        return message
    }

    override fun view() = view
}
