package com.viewBindingTemplate.customclasses.validations

import android.view.View

class EmptyValidator(val view: View, private val editable: String, val message: String) :
    Validator {

    override fun isValid(): Boolean {
        return editable.isNotEmpty()
    }

    override fun view() = view

    override fun message(): String {
        return message
    }
}