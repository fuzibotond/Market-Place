package com.example.market_place.fragments.authorization

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.market_place.R
import com.example.market_place.databinding.FragmentRegisterBinding
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.RegisterViewModel
import com.example.market_place.viewmodels.RegisterViewModelFactory
import kotlinx.coroutines.launch
import java.util.regex.Pattern


class RegisterFragment : Fragment() {
    private lateinit var registerViewModel: RegisterViewModel
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = RegisterViewModelFactory(this.requireContext(), Repository())
        registerViewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        initialize()
        settingListeners()
        return binding.root
    }

    private fun settingListeners() {
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
        binding.phoneNumberInput.doOnTextChanged { text, start, before, count ->
            if (count==binding.phoneNumberInputLayout.counterMaxLength){
                binding.phoneNumberInputLayout.helperText = "No More!"
                binding.phoneNumberInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_error)
                binding.passwordInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_color))


            }
        }
        binding.btnRegister.setOnClickListener {
            if (!binding.emailInput.text.toString().isEmpty() || !binding.passwordInput.text.toString().isEmpty()){
                registerViewModel.user.value?.let {
                    it.first_name = binding.firstNameInput.text.toString()
                    it.last_name = binding.lastNameInput.text.toString()
                    it.email = binding.emailInput.text.toString()
                    it.password = binding.passwordInput.text.toString()
                    it.phone_number = binding.phoneNumberInput.text.toString()
                }
                lifecycleScope.launch {
                    registerViewModel.register()
                }
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }else{
                Toast.makeText(context,"Please fill the required fields!", Toast.LENGTH_LONG).show()
            }
        }
        binding.alreadyHaveAnAccountClick.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

    }

    private fun initialize() {
        registerViewModel.code.observe(viewLifecycleOwner){
            Log.d("xxx", "navigate to list")
            if (registerViewModel.code.value!! == 200)
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

}