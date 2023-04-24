package com.viewBindingTemplate.customclasses.singleClickListener

import android.view.View

/**
 * Set On Single Click Listener
 * */
fun View.setOnSingleClickListener(onSingleClickListener: OnSingleClickListener) {
    SingleClickListenerHandler(view = this, onSingleClickListener = onSingleClickListener)
}


/**
 * Set On Single Click Listener
 * */
fun View.setOnSingleClickListener(callBack: View.() -> Unit) {
    SingleClickListenerHandler(
        view = this,
        onSingleClickListener = object : OnSingleClickListener {
            override fun onClick(view: View?) {
                callBack()
            }
        })
}

