package com.example.market_place.fragments.market

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.R
import com.example.market_place.databinding.FragmentLoginBinding
import com.example.market_place.databinding.FragmentProfileBinding
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.*
import com.smarteist.autoimageslider.Transformations.TossTransformation
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userInfoViewModel:UserInfoViewModel
    private lateinit var updateUserInfoViewModel:UpdateUserInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = UserInfoViewModelFactory( Repository())
        userInfoViewModel = ViewModelProvider(this, factory).get(UserInfoViewModel::class.java)
        val factoryUpdate = UpdateUserInfoViewModelFactory(this.requireContext(),Repository())
        updateUserInfoViewModel = ViewModelProvider(this,factoryUpdate).get(UpdateUserInfoViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding = FragmentProfileBinding.inflate(inflater, container, false)
        initialize()
        settingListener()
        return binding.root
    }

    private fun settingListener() {
        binding.btnPublish.setOnClickListener {
            if (!binding.emailInput.text.toString().isEmpty()){
                updateUserInfoViewModel.user.value?.phone_number = binding.phoneNumberInput.text.toString()
                updateUserInfoViewModel.user.value?.email = binding.emailInput.text.toString()
                updateUserInfoViewModel.user.value?.username = binding.usernameInput.text.toString()
                Toast.makeText(this.requireActivity(), "Profile data published succesfully! Dear ${MarketPlaceApplication.user!!.username}",Toast.LENGTH_SHORT).show()
            }
            lifecycleScope.launch {
                updateUserInfoViewModel.updateUserData()
            }
            findNavController().navigate(R.id.action_profileFragment_to_profileDetailsFragment)
        }
    }

    private fun initialize() {
        binding.emailInput.hint = userInfoViewModel.user.value?.email
        binding.phoneNumberInput.hint = userInfoViewModel.user.value?.phone_number
        binding.usernameInput.hint = userInfoViewModel.user.value?.username
    }


}