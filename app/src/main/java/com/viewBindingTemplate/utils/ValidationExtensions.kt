package com.viewBindingTemplate.utils

import android.text.InputFilter
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.util.Patterns
import com.hbb20.CountryCodePicker
import com.viewBindingTemplate.remote.HttpStatusCode
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * Get Country Code
 * */
fun CountryCodePicker.countryCode(): String = selectedCountryCodeWithPlus

fun String?.isRequired(): Boolean = TextUtils.isEmpty(this?.trim().orEmpty())

/**
 * Get iSO Code
 * */
fun CountryCodePicker.isoCode(): String = selectedCountryNameCode

fun String?.isValidEmail(): Boolean =
    Patterns.EMAIL_ADDRESS.matcher(this?.trim().orEmpty()).matches()

fun String?.isValidPassword(): Boolean {
    val pattern = Pattern.compile(HttpStatusCode.PASSWORD_REGEX)
    return pattern.matcher(this.orEmpty()).matches()
}

infix fun String?.isMatch(secondText: String?) =
    this?.trim().orEmpty().contentEquals(secondText?.trim().orEmpty(), ignoreCase = false)

fun String?.isValidNumber() = TextUtils.isDigitsOnly(this?.trim().orEmpty())

const val ALPHABET_SPACE_REGEX = "^[a-zA-Z ]+$"
const val ALPHABET_WITHOUT_SPACE_REGEX = "^[a-zA-Z]+$"

fun alphabetFilter(regex: String = ALPHABET_SPACE_REGEX): InputFilter {
    return object : InputFilter {
        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            var keepOriginal = true
            val sb = StringBuilder(end - start)
            for (i in start until end) {
                val c = source[i]
                if (isCharAllowed(c)) // put your condition here
                    sb.append(c) else keepOriginal = false
            }
            return if (keepOriginal) null else {
                if (source is Spanned) {
                    val sp = SpannableString(sb)
                    TextUtils.copySpansFrom(source, start, sb.length, null, sp, 0)
                    sp
                } else {
                    sb
                }
            }
        }

        private fun isCharAllowed(c: Char): Boolean {
            val ps = Pattern.compile(regex)
            val ms: Matcher = ps.matcher(c.toString())
            return ms.matches()
        }
    }
}