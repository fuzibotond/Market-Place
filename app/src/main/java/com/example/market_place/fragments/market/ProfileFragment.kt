package com.example.market_place.fragments.market

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
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
import java.util.regex.Pattern
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Editable
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userInfoViewModel:UserInfoViewModel
    private lateinit var updateUserInfoViewModel:UpdateUserInfoViewModel
    val sharedViewModel:SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = UserInfoViewModelFactory( Repository())
        userInfoViewModel = ViewModelProvider(this, factory).get(UserInfoViewModel::class.java)
        val factoryUpdate = UpdateUserInfoViewModelFactory(this.requireActivity(),Repository())
        updateUserInfoViewModel = ViewModelProvider(this,factoryUpdate).get(UpdateUserInfoViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding = FragmentProfileBinding.inflate(inflater, container, false)
        initialize()
        handleThatBackPress()
        settingListener()
        return binding.root
    }

    private fun settingListener() {
        binding.btnPublish.setOnClickListener {
            if (!binding.detailsEmailInput.text.toString().isEmpty()){
                updateUserInfoViewModel.user.value?.phone_number = binding.detailsPhoneNumberInput.text.toString()
                updateUserInfoViewModel.user.value?.email = binding.detailsEmailInput.text.toString()
                updateUserInfoViewModel.user.value?.username = binding.detailsUsernameInput.text.toString()
            }
            lifecycleScope.launch {
                updateUserInfoViewModel.updateUserData()
            }
            saveUserData()

        }

    }

    private fun initialize() {
        if (sharedViewModel.detailedUser.value!=null){
            lifecycleScope.launch {
                userInfoViewModel.getUserInfo(sharedViewModel.detailedUser.value!!)
            }
        }
        userInfoViewModel.user.observe(viewLifecycleOwner){
            binding.detailsEmailInput.setText(userInfoViewModel.user.value?.email)
            binding.detailsPhoneNumberInput.setText(userInfoViewModel.user.value?.phone_number)
            binding.detailsUsernameInput.setText(userInfoViewModel.user.value?.username)
        }


    }
    private fun handleThatBackPress(){
        val callback: OnBackPressedCallback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                AlertDialog.Builder(requireActivity())
                    .setTitle("Back?")
                    .setMessage("Your modifies won't be published...Are you sure about that?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("OK"){ _,_ ->
                        findNavController().navigate(R.id.marketPlaceFragment)
                    }
                    .show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
    private fun saveUserData(){
        val sharedPreferences:SharedPreferences = requireContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putString("TOKEN_KEY", MarketPlaceApplication.token)
            putString("USERNAME_KEY", MarketPlaceApplication.username)
            updateUserInfoViewModel.user.value?.let { putLong("CREATION_TIME_KEY", it.creation_time) }

        }.apply()
        Toast.makeText(this.requireContext(), "Saved ${ MarketPlaceApplication.username}s data!", Toast.LENGTH_SHORT).show()
    }
    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
}