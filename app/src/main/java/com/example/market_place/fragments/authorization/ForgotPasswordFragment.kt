package com.example.market_place.fragments.authorization

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.market_place.R
import com.example.market_place.databinding.FragmentForgotPasswordBinding
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.ResetPasswordViewModel
import com.example.market_place.viewmodels.ResetPasswordViewModelFactory
import kotlinx.coroutines.launch

class ForgotPasswordFragment : Fragment() {
    private lateinit var resetPasswordViewModel: ResetPasswordViewModel
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ResetPasswordViewModelFactory(this.requireContext(), Repository())
        resetPasswordViewModel = ViewModelProvider(this, factory).get(ResetPasswordViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        initialize()
        settingListeners()
        return binding.root
    }

    private fun settingListeners() {

        binding.btnEmailMe.setOnClickListener {
            if (!binding.emailInput.text.toString().isEmpty()){
                resetPasswordViewModel.user.value?.let {
                    it.email = binding.emailInput.text.toString()
                    it.username = binding.emailInput.text.toString()
                }
            }
            lifecycleScope.launch {
                resetPasswordViewModel.resetPassword()
            }
            if (resetPasswordViewModel.result.value?.code == 200){
                Toast.makeText(requireActivity(), resetPasswordViewModel.result.value?.message, Toast.LENGTH_SHORT)
                findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)

            }
        }
        binding.newTo3reaForgotPass.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
        }

    }

    private fun initialize() {
        resetPasswordViewModel.result.observe(viewLifecycleOwner){
            if (resetPasswordViewModel.result.value?.code == 200){
                Toast.makeText(requireActivity(), resetPasswordViewModel.result.value?.message, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
            }
            Log.d("xxx", "Status: ${resetPasswordViewModel.result.value?.code} - ${resetPasswordViewModel.result.value?.message}")
        }
    }

}