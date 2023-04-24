package com.viewBindingTemplate.utils

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.view.View
import androidx.annotation.ColorRes
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat

fun View.getString(stringId: Int) = context.getString(stringId)

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.showPopupMenu(list: List<String>, returnPos: (Int) -> Unit) {
    val popupMenu = PopupMenu(context, this)
    list.forEachIndexed { index, name ->
        popupMenu.menu.add(
            0, // groupId
            index, //itemId
            0, //order
            name.makeCap()
        )
    }

    popupMenu.setOnMenuItemClickListener { item ->
        returnPos(item.itemId)
        true
    }
    popupMenu.show()
}

fun View.disable(@ColorRes color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        background.colorFilter =
            BlendModeColorFilter(ContextCompat.getColor(this.context, color), BlendMode.SRC_ATOP)
    else
        background.setColorFilter(ContextCompat.getColor(this.context, color),
            PorterDuff.Mode.MULTIPLY)
    isClickable = false
}

fun View.enable(@ColorRes color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        background.colorFilter =
            BlendModeColorFilter(ContextCompat.getColor(this.context, color), BlendMode.SRC_ATOP)
    else
        background.setColorFilter(ContextCompat.getColor(this.context, color),
            PorterDuff.Mode.MULTIPLY)
    isClickable = true
}