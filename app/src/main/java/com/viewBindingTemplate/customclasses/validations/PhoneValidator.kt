package com.viewBindingTemplate.customclasses.validations

import android.view.View
import com.hbb20.CountryCodePicker
import com.viewBindingTemplate.customclasses.phoneNumberValidation.PhoneNumberValidator
import com.viewBindingTemplate.utils.countryCode
import com.viewBindingTemplate.utils.isoCode

class PhoneValidator(
    val view: View,
    private val editable: String,
    private val ccp: CountryCodePicker,
    val message: String,
) : Validator {
    override fun isValid(): Boolean {
        return PhoneNumberValidator(view.context).checkValidPhoneNumber(
            countryCode = ccp.countryCode(), phoneNumber = editable, isoCode = ccp.isoCode()
        )
    }

    override fun view() = view

    override fun message(): String {
        return message
    }
}