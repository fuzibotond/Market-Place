package com.example.market_place.fragments.authorization

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.coroutines.launch
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
        settingListeners()
        return binding.root
    }
   //TODO: Make stroke list red
    private fun settingListeners() {
//        val EMAIL_PATTERN:String = "^[0-9a-z\\.-]+@([0-9a-z-]+\\.)+[a-z]{4,5}\$"
//        val patternForEmail = Pattern.compile(EMAIL_PATTERN)
        binding.emailInput.doOnTextChanged { text, start, before, count ->
//            val emailTocheck = (text!!.reversed()).takeWhile { it=='.' }
//            if (Patterns.EMAIL_ADDRESS.matcher(emailTocheck.toString()).matches() ){
//                Log.d("email", emailTocheck.toString());
//                binding.emailInputLayout.helperText = "Not an email type!"
//                binding.emailInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_error)
//                binding.emailInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_error))
//            }
            if (text!!.toString().isEmpty()){
                binding.emailInputLayout.helperText = "This field is required!"
                binding.emailInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_error)
                binding.emailInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_error))
                binding.emailInputLayout.setEndIconTintList(resources.getColorStateList(R.color.text_input_box_stroke_error))
                binding.emailInputLayout.setEndIconDrawable(R.drawable.ic_error_mark)
            }
            else{
                binding.emailInputLayout.helperText = ""
                binding.emailInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_color)
                binding.emailInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_color))
                binding.emailInputLayout.setEndIconTintList(resources.getColorStateList(R.color.text_input_box_stroke_color))
                binding.emailInputLayout.setEndIconDrawable(R.drawable.ic_circle_check)
            }
        }
       val PASSWORD_PATTERN:String = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
       val pattern = Pattern.compile(PASSWORD_PATTERN)

       binding.passwordInput.doOnTextChanged { text, start, before, count ->
           if (!text!!.toString().isEmpty()){
               binding.passwordInputLayout.helperText = "This field is required"
               binding.passwordInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_error)
               binding.passwordInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_error))
           }
           if ( !pattern.matcher(text).matches()){
               binding.passwordInputLayout.helperText = "Too weak password!"
               binding.passwordInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_error)
               binding.passwordInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_error))
           }
           else{
               binding.passwordInputLayout.setHelperTextColor(resources.getColorStateList(R.color.text_input_box_stroke_color))
               binding.passwordInputLayout.helperText = "Strong password!"
               binding.passwordInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_color)
               binding.passwordInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_color))
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