package com.viewBindingTemplate.viewpagerdotsindicator.attacher

import android.database.DataSetObserver
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.viewBindingTemplate.viewpagerdotsindicator.BaseDotsIndicator
import com.viewBindingTemplate.viewpagerdotsindicator.OnPageChangeListenerHelper

internal class ViewPagerAttacher :
    DotsIndicatorAttacher<ViewPager, PagerAdapter>() {
    override fun getAdapterFromAttachable(attachable: ViewPager): PagerAdapter? = attachable.adapter

    override fun registerAdapterDataChangedObserver(
        attachable: ViewPager,
        adapter: PagerAdapter,
        onChanged: () -> Unit
    ) {
        adapter.registerDataSetObserver(object : DataSetObserver() {
            override fun onChanged() {
                super.onChanged()
                onChanged()
            }
        })
    }

    override fun buildPager(attachable: ViewPager, adapter: PagerAdapter): BaseDotsIndicator.Pager {
        return object : BaseDotsIndicator.Pager {
            var onPageChangeListener: ViewPager.OnPageChangeListener? = null

            override val isNotEmpty: Boolean get() = isNotEmpty
            override val currentItem: Int get() = attachable.currentItem
            override val isEmpty: Boolean get() = isEmpty
            override val count: Int get() = attachable.adapter?.count ?: 0

            override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
                attachable.setCurrentItem(item, smoothScroll)
            }

            override fun removeOnPageChangeListener() {
                onPageChangeListener?.let { attachable.removeOnPageChangeListener(it) }
            }

            override fun addOnPageChangeListener(
                onPageChangeListenerHelper:
                OnPageChangeListenerHelper
            ) {
                onPageChangeListener = object : ViewPager.OnPageChangeListener {
                    override fun onPageScrolled(
                        position: Int, positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        onPageChangeListenerHelper.onPageScrolled(position, positionOffset)
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                    }

                    override fun onPageSelected(position: Int) {
                    }
                }
                attachable.addOnPageChangeListener(onPageChangeListener!!)
            }
        }
    }
}