package com.example.market_place.fragments.market

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.market_place.R
import com.example.market_place.adapter.TabAdapter
import com.example.market_place.databinding.FragmentLoginBinding
import com.example.market_place.databinding.FragmentMyFaresBinding
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.ListOrderViewModel
import com.example.market_place.viewmodels.ListOrderViewModelFactory
import com.example.market_place.viewmodels.SharedViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch


class MyFaresFragment : Fragment() {
    private var _binding: FragmentMyFaresBinding? = null
    private val binding get() = _binding!!
    val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyFaresBinding.inflate(inflater, container, false)
        val adapter = TabAdapter(requireActivity().supportFragmentManager, lifecycle)
        binding.myFaresViewpager.adapter = adapter
        TabLayoutMediator(binding.ongoingSalesAndOrders,  binding.myFaresViewpager){ tab, position->
            when(position){
                0->{
                    tab.text ="Ongoing Sales"
                }
                1->{
                    tab.text ="Ongoing Orders"
                }
            }
        }.attach()
        return binding.root
    }


}