package com.example.market_place.fragments.market

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.market_place.R
import com.example.market_place.adapter.TabAdapter
import com.example.market_place.databinding.FragmentLoginBinding
import com.example.market_place.databinding.FragmentMyFaresBinding
import com.google.android.material.tabs.TabLayout


class MyFaresFragment : Fragment() {
    private var _binding: FragmentMyFaresBinding? = null
    private val binding get() = _binding!!
    lateinit var tablayout:TabLayout
    lateinit var viewPager: ViewPager
    lateinit var tabAdapter: TabAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyFaresBinding.inflate(inflater, container, false)
        binding.ongoingSalesAndOrders.setupWithViewPager(binding.myFaresViewpager)
        tabAdapter = TabAdapter(parentFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        tabAdapter.addFragment(OngoingSalesFragment(), "Ongoing Sales")
        tabAdapter.addFragment(OngoingOrdersFragment(), "Ongoing Orders")
        binding.myFaresViewpager.adapter = tabAdapter
        return binding.root
    }

}