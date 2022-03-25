package com.mingyang.currentview.text

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class TestViewPager(fragmentManager: FragmentManager, private val list: ArrayList<Fragment>) :
    FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Fragment = list[position]

}