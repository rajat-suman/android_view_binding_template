package com.viewBindingTemplate.customclasses.validations

import android.view.View
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import com.hbb20.CountryCodePicker
import com.viewBindingTemplate.utils.showToast

class Validation {

    private var validators: ArrayList<Validator>? = null

    private val shake by lazy {
        TranslateAnimation(0f, 10f, 0f, 0f).apply {
            duration = 500
            interpolator = CycleInterpolator(5F)
        }
    }

    init {
        validators = ArrayList()
    }


    fun isEmpty(view: View, editable: String, message: String): Validation {
        validators?.add(EmptyValidator(view, editable, message))
        return this
    }

    fun isTermsSelected(view: View, isSelected: Boolean, message: String): Validation {
        validators?.add(BooleanValidator(view, isSelected, message))
        return this
    }

    fun isEmailValid(view: View, editable: String, message: String): Validation {
        validators?.add(EmailValidator(view, editable, message))
        return this
    }

    fun isPhoneValid(
        view: View,
        editable: String,
        ccp: CountryCodePicker,
        message: String,
    ): Validation {
        validators?.add(PhoneValidator(view, editable, ccp, message))
        return this
    }

    fun isValidPassword(view: View, editable: String, message: String): Validation {
        validators?.add(PasswordValidator(view, editable, message))
        return this
    }

    fun isValidOtpLength(
        view: View,
        editable: String,
        otpMaxLength: Int,
        message: String,
    ): Validation {
        validators?.add(OtpValidator(view, editable, otpMaxLength, message))
        return this
    }

    fun isMinimumLength(
        view: View,
        editable: String,
        minimumLength: Int,
        message: String,
    ): Validation {
        validators?.add(MinimumLengthValidator(view, editable, minimumLength, message))
        return this
    }

    fun areEqual(view: View, editable: String, editable2: String, message: String): Validation {
        validators?.add(IsMatched(view, editable, editable2, message))
        return this
    }

    fun isNotMatched(
        view: View,
        editable: String,
        editable2: String,
        message: String,
    ): Validation {
        validators?.add(IsNotMatched(view, editable, editable2, message))
        return this
    }

    fun isValid(): Boolean {
        validators.let {
            it?.forEach { validator ->
                if (!validator.isValid()) {
                    validator.view().startAnimation(shake)
                    showToast(validator.message() ?: "")
                    return false
                }
            }
        }
        return true
    }

}