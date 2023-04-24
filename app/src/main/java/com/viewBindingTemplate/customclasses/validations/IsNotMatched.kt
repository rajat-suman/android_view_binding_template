package com.viewBindingTemplate.customclasses.validations

import android.view.View
import com.viewBindingTemplate.utils.isMatch

class IsNotMatched(
    val view: View,
    private val editable: String,
    private val editable2: String,
    val message: String,
) : Validator {
    override fun isValid(): Boolean {
        return !(editable isMatch editable2)
    }

    override fun view() = view
    override fun message(): String {
        return message
    }
}