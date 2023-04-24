package com.viewBindingTemplate.customclasses.phoneNumberValidation


import android.content.Context
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil

/**
 * Phone Number Validation
 * */
class PhoneNumberValidator(context: Context) {

    /**
     * Phone Number Validation
     * */
    private var phoneUtilInstance: PhoneNumberUtil? = null


    /**
     * Initializer
     * */
    init {
        phoneUtilInstance = PhoneNumberUtil.createInstance(context)
    }


    /**
     * Check Valid Phone Number
     * */
    fun checkValidPhoneNumber(countryCode: String, phoneNumber: String, isoCode: String): Boolean {
        val number = phoneUtilInstance?.parse("$countryCode$phoneNumber", isoCode)
        return phoneUtilInstance?.isValidNumber(number) ?: true
    }

}