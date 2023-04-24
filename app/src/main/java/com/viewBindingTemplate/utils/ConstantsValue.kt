package com.viewBindingTemplate.utils

object ConstantsValue {

    const val ROLE = 2
    const val CURRENCY_SYMBOL = "$"

    /**
     * Profile Constants Start
     * */

    const val USER_CREATED_BUT_NOT_VERIFIED: Short = 1
    const val USER_VERIFIED: Short = 2
    const val USER_PROFILE_COMPLETED: Short = 4

    /**
     * Profile Constants END
     * */

    /**
     * Digital Question Constant
     * */
    const val MULTIPLE_CLICK = 2 // keys on back ---> "radio"
    const val SINGLE_CLICK  = 1 // keys on back ---> "checkbox"
    const val INPUT_FIELD = 3 // keys on back ---> "textbox"
}