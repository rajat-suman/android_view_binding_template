package com.viewBindingTemplate.customclasses.validations

import android.view.View

class BooleanValidator(
    private val view: View,
    private val isSelected: Boolean,
    private val message: String,
) : Validator {

    override fun isValid(): Boolean {
        return isSelected
    }

    override fun view() = view

    override fun message(): String {
        return message
    }
}