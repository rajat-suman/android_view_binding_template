package com.viewBindingTemplate.customclasses.validations

import android.view.View

interface Validator {
    fun view(): View
    fun isValid(): Boolean
    fun message(): String?
}