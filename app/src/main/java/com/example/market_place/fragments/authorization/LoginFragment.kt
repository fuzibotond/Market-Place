package com.example.market_place.fragments.authorization

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
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
import com.example.market_place.AuthorizedActivity
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.R
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.LoginViewModel
import com.example.market_place.viewmodels.LoginViewModelFactory
import com.example.market_place.databinding.FragmentLoginBinding
import com.example.market_place.utils.NetworkHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.sql.Time
import java.sql.Timestamp
import java.util.*
import java.util.regex.Pattern

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
        loadData()
        settingListeners()
        handleThatBackPress()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        val sharedPreferences:SharedPreferences = requireContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedToken = sharedPreferences.getString("TOKEN_KEY", null)
        val savedUsername = sharedPreferences.getString("USERNAME_KEY", null)
        val savedCreationTime = sharedPreferences.getLong("CREATION_TIME_KEY", 0)
        val savedRefreshTime = sharedPreferences.getLong("REFRESH_TIME_KEY", 0)
        if (savedCreationTime+savedRefreshTime > System.currentTimeMillis()){
            if (savedToken != null) {
                MarketPlaceApplication.token = savedToken
            }
            loginViewModel.token.value = savedToken
        }else{
            sharedPreferences.edit().clear().commit()
            Toast.makeText(requireContext(), "Your session has been expired! You need to log in again...", Toast.LENGTH_SHORT).show()
        }
        if (savedUsername != null) {
            MarketPlaceApplication.username = savedUsername
        }

    }

    private fun settingListeners() {

        val PASSWORD_PATTERN:String = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
        val pattern = Pattern.compile(PASSWORD_PATTERN)
        binding.passwordInputLayout.setEndIconTintList(resources.getColorStateList(R.color.wrapedWhite))
        binding.passwordInput.doOnTextChanged { text, start, before, count ->
            if (!text.toString().isEmpty()){
                binding.passwordInputLayout.helperText = ""
                binding.passwordInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_color)
                binding.passwordInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_color))
            }
        }
        binding.emailInput.doOnTextChanged { text, start, before, count ->
            if (!text.toString().isEmpty()){
                binding.passwordInputLayout.helperText = ""
                binding.passwordInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_color)
                binding.passwordInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_color))
            }
        }
        binding.emailInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (binding.passwordInput.text.toString().isEmpty()){
                    binding.passwordInputLayout.helperText = "This field is required"
                    binding.passwordInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_error)
                    binding.passwordInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_error))
                }else {
                    binding.passwordInputLayout.helperText = ""
                    binding.passwordInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_color)
                    binding.passwordInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_color))
                }
            } else {
                if (binding.passwordInput.text.toString().isEmpty()){
                    binding.passwordInputLayout.helperText = "This field is required"
                    binding.passwordInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_error)
                    binding.passwordInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_error))
                }else {
                    binding.passwordInputLayout.helperText = ""
                    binding.passwordInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_color)
                    binding.passwordInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_color))
                }
                if (binding.emailInput.text!!.toString().isEmpty()){
                    binding.emailInputLayout.helperText = "This field is required!"
                    binding.emailInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_error)
                    binding.emailInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_error))
                    binding.emailInputLayout.setEndIconTintList(resources.getColorStateList(R.color.text_input_box_stroke_error))
                    binding.emailInputLayout.setEndIconDrawable(R.drawable.ic_error_mark)
                }else {
                    binding.passwordInputLayout.helperText = ""
                    binding.passwordInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_color)
                    binding.passwordInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_color))
                    binding.emailInputLayout.setEndIconDrawable(R.drawable.ic_circle_check)
                }
            }
        }
        binding.emailInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (binding.passwordInput.text.toString().isEmpty()){
                    binding.passwordInputLayout.helperText = "This field is required"
                    binding.passwordInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_error)
                    binding.passwordInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_error))
                }else {
                    binding.passwordInputLayout.helperText = ""
                    binding.passwordInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_color)
                    binding.passwordInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_color))
                }
            } else {
                if (binding.passwordInput.text.toString().isEmpty()){
                    binding.passwordInputLayout.helperText = "This field is required"
                    binding.passwordInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_error)
                    binding.passwordInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_error))
                }else {
                    binding.passwordInputLayout.helperText = ""
                    binding.passwordInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_color)
                    binding.passwordInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_color))
                }

            }
        }
        var buttonClickCounter = 0
        binding.btnLogIn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            buttonClickCounter++
            loginViewModel.user.value.let {
                if (it != null) {
                    it.username = binding.emailInput.text.toString()
                }
                if (it != null) {
                    it.password = binding.passwordInput.text.toString()
                }
            }
            if (NetworkHelper.isNetworkConnected(this.requireActivity())){
                lifecycleScope.launch {
                    binding.btnLogIn.isEnabled = !loginViewModel.login()
                }
            }else{
                lifecycleScope.launch {
                    delay(1000)
                    Toast.makeText(requireActivity(), "There is no internet", Toast.LENGTH_SHORT).show()
                }
                binding.progressBar.visibility = View.GONE

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
        binding.progressBar.visibility = View.GONE
        loginViewModel.refresh_time.observe(viewLifecycleOwner){
            saveUserData()
//            Log.d("xxx", "save")
        }

        loginViewModel.token.observe(viewLifecycleOwner){
            Log.d("xxx", "navigate to list")

            val intent = Intent(context, AuthorizedActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, "You are logged in!${loginViewModel.user.value!!.username}")
            }
            startActivity(intent)
        }

    }

    private fun saveUserData(){
        val sharedPreferences:SharedPreferences = requireContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        Log.d("xxx", loginViewModel.creation_time.value.toString())
        editor.apply{
            putString("TOKEN_KEY", MarketPlaceApplication.token)
            putString("USERNAME_KEY", MarketPlaceApplication.username)
            loginViewModel.creation_time.value?.let { putLong("CREATION_TIME_KEY", it) }
            loginViewModel.refresh_time.value?.let { putLong("REFRESH_TIME_KEY", it) }

        }.apply()
        Toast.makeText(this.requireContext(), "Saved ${ MarketPlaceApplication.username}s data!", Toast.LENGTH_SHORT).show()
    }
    internal fun String.toIntColor() = Integer.parseInt(this.replaceFirst("#", ""), 16)
    private fun handleThatBackPress(){
        val callback: OnBackPressedCallback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                AlertDialog.Builder(requireActivity())
                    .setTitle("Exit!")
                    .setMessage("Are you sure about that? We will clear your saved data.")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("OK"){ _,_ ->
                        clearDate()
                        requireActivity().finish()
                    }
                    .show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
    private fun clearDate() {
        val sharedPreferences:SharedPreferences = requireContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().commit()

    }
}