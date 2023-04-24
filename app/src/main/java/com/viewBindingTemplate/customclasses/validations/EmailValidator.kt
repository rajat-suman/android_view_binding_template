package com.viewBindingTemplate.customclasses.validations

import android.view.View
import com.viewBindingTemplate.utils.isValidEmail

class EmailValidator(val view: View, private val editable: String, val message: String) :
    Validator {

    override fun isValid(): Boolean {
        return editable.isValidEmail()
    }

    override fun view() = view

    override fun message() = message
}