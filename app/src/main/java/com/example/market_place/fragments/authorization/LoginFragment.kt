package com.example.market_place.fragments.authorization

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.market_place.AuthorizedActivity
import com.example.market_place.R
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.LoginViewModel
import com.example.market_place.viewmodels.LoginViewModelFactory
import com.example.market_place.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = LoginViewModelFactory(this.requireContext(), Repository())
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        initialize()
        settingListeners()
        return binding.root
    }
   //TODO: Make stroke list red
    private fun settingListeners() {
        binding.emailInput.doOnTextChanged { text, start, before, count ->
            if (!text!!.isEmpty() && text!!.length<5 ){
                binding.emailInput.error = "Too short username!"
            }
        }
        binding.btnLogIn.setOnClickListener {
            loginViewModel.user.value.let {
                if (it != null) {
                    it.username = binding.emailInput.text.toString()
                }
                if (it != null) {
                    it.password = binding.passwordInput.text.toString()
                }
            }
            lifecycleScope.launch {
                loginViewModel.login()
            }

        }
       binding.btnSignUp.setOnClickListener {
           findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
       }
       binding.forgotPasswordClick.setOnClickListener {
           findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
       }
    }

    private fun initialize() {
        loginViewModel.token.observe(viewLifecycleOwner){
            Log.d("xxx", "navigate to list")
            val intent = Intent(context, AuthorizedActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, "You are logged in!${loginViewModel.user.value!!.username}")
            }
            //findNavController().navigate(R.id.action_loginFragment_to_listFragment)
            startActivity(intent)
        }
    }
    internal fun String.toIntColor() = Integer.parseInt(this.replaceFirst("#", ""), 16)
}