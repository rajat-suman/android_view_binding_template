package com.viewBindingTemplate.adapters.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class PagingGenericAdapter<T : Any>(
    @LayoutRes private val layoutId: Int,
    @LayoutRes private val placeHolder: Int? = null,
) :
    PagingDataAdapter<T, PagingGenericAdapter.ViewHolder>(GenericDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = when (viewType) {
            -1 -> placeHolder
            else -> layoutId
        } ?: layoutId
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == null) -1
        else super.getItemViewType(position)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}