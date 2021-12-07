package com.example.market_place.fragments.market

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.R
import com.example.market_place.databinding.FragmentLoginBinding
import com.example.market_place.databinding.FragmentProfileDetailsBinding
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.UserInfoViewModel
import com.example.market_place.viewmodels.UserInfoViewModelFactory


class ProfileDetailsFragment : Fragment() {

    private var _binding: FragmentProfileDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var userInfoViewModel: UserInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileDetailsBinding.inflate(inflater, container, false)
        val factory = UserInfoViewModelFactory( Repository())
        userInfoViewModel = ViewModelProvider(this, factory).get(UserInfoViewModel::class.java)

        initialize()

        return binding.root
    }

    private fun initialize() {
        userInfoViewModel.getUserInfo()
        binding.profileEmailTextview.setText( MarketPlaceApplication.user_email)
        binding.profilePhoneNumberTextview.text = MarketPlaceApplication.user_phone_number
        binding.profileUsernameTextview.text = MarketPlaceApplication.username
    }

}