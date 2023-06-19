package com.viewBindingTemplate.adapters.recycler

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

/** You pass <M> data class type
 * and its received in <T> data
 * class type.
 * */

class GenericDiffUtil<M : Any> : DiffUtil.ItemCallback<M>() {
    /** Check old item and new item are same
     *  If they are both same */
    override fun areItemsTheSame(oldItem: M, newItem: M): Boolean {
        return oldItem.equals(newItem)
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: M, newItem: M): Boolean {
        return oldItem.equals(newItem)
    }
}