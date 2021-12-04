package com.example.market_place.adapter


import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabAdapter (val fm:FragmentManager, val behavior: Int): FragmentPagerAdapter(fm, behavior) {

    val fragmentArrayList:ArrayList<Fragment> = arrayListOf()
    val fragmentTitle: ArrayList<String> = arrayListOf()

    override fun getCount(): Int {
        return fragmentArrayList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentArrayList.get(position)
    }

    fun addFragment(fragment:Fragment, title:String){
        fragmentArrayList.add(fragment)
        fragmentTitle.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitle.get(position)
    }
}