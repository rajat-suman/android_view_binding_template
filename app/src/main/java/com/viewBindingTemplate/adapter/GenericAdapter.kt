package com.viewBindingTemplate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/** <M> is used for Data Class for specific type */
/** Pass a <M> in DiffUtil Class */

abstract class GenericAdapter<M : Any>(@LayoutRes private val layoutId: Int) :
    ListAdapter<M, GenericAdapter.ViewHolder>(GenericDiffUtil<M>()) {

    private val listSupplied = ArrayList<M>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun updateAt(position: Int,item: M) {
        val mutableList = currentList.toMutableList()
        mutableList[position] = item
        notifyItemChanged(position,item)
    }

    fun submitElements(list: List<M>) {
        listSupplied.clear()
        listSupplied.addAll(list)
        submitList(listSupplied)
    }

    override fun getItemId(position: Int): Long {
        return currentList[position].hashCode().toLong()
    }

    fun isNullOrEmpty() = currentList.isEmpty()

    fun isNotEmpty() = currentList.isEmpty().not()

    fun append(list: List<M>) {
        listSupplied.addAll(list)
        notifyItemRangeChanged(0, currentList.size, currentList)
    }

    fun deleteElement(position: Int) {
        listSupplied.removeAt(position)
        submitList(listSupplied)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, listSupplied.size)
    }

    fun getItemAt(position: Int) = listSupplied[position]

    fun currentItem(position: Int) = currentList.getOrNull(position)

    fun getAllItems() = listSupplied

    /** Animation Function */
    private fun View.setAnimation() {
        val animation: Animation = AnimationUtils.loadAnimation(
            this.context, androidx.navigation.ui.R.anim.nav_default_enter_anim
        )
        this.startAnimation(animation)
    }


}
