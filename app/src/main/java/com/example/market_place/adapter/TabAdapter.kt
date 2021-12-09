package com.example.market_place.adapter


import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.market_place.fragments.market.OngoingOrdersFragment
import com.example.market_place.fragments.market.OngoingSalesFragment

class TabAdapter ( fm:FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fm,lifecycle) {


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
       return when(position){
            0->{
                 OngoingSalesFragment()
            }
            1->{
                 OngoingOrdersFragment()
            }
            else->{
                Fragment()
            }
        }
    }

}