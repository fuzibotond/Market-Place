package com.example.market_place.fragments.market

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.R
import com.example.market_place.databinding.FragmentLoginBinding
import com.example.market_place.databinding.FragmentProfileDetailsBinding
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.SharedViewModel
import com.example.market_place.viewmodels.UserInfoViewModel
import com.example.market_place.viewmodels.UserInfoViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ProfileDetailsFragment : Fragment() {

    private var _binding: FragmentProfileDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var userInfoViewModel: UserInfoViewModel
    val sharedViewModel:SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileDetailsBinding.inflate(inflater, container, false)
        handleThatBackPress()

        getUserDataFromBackend()
        initialize()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = UserInfoViewModelFactory( Repository())
        userInfoViewModel = ViewModelProvider(this, factory).get(UserInfoViewModel::class.java)
    }
    private fun getUserDataFromBackend() {
        if (sharedViewModel.detailedUser.value!=null){
            lifecycleScope.launch {
                userInfoViewModel.getUserInfo(sharedViewModel.detailedUser.value!!)
            }
        }
    }

    private fun initialize() {
        userInfoViewModel.user.observe(viewLifecycleOwner){
            binding.profileEmailTextview.setText( userInfoViewModel.user.value?.email)
            binding.profilePhoneNumberTextview.setText( userInfoViewModel.user.value?.phone_number)
            binding.profileUsernameTextview.setText( userInfoViewModel.user.value?.username)
        }
        binding.btnSendAChatMessage.visibility = View.GONE

    }
    private fun handleThatBackPress(){
        val callback: OnBackPressedCallback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.marketPlaceFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}