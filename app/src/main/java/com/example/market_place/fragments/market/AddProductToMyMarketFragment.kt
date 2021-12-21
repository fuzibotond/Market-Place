package com.example.market_place.fragments.market


import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.R
import com.example.market_place.databinding.FragmentAddProductToMyMarketBinding
import com.example.market_place.model.*
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.*
import kotlinx.coroutines.launch
class AddProductToMyMarketFragment : Fragment(){

    private var _binding: FragmentAddProductToMyMarketBinding? = null
    private val binding get() = _binding!!
    lateinit var addProductViewModel:AddProductViewModel
    lateinit var updateAssetViewModel:UpdateAssetViewModel
    private val sharedViewModel:SharedViewModel by activityViewModels()
    var currentProduct = MutableLiveData<Product>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddProductToMyMarketBinding.inflate(
            inflater, container, false)
        restoreKeptData()
        handleThatBackPress()
        settingListener()


        return binding.root
    }

    private fun restoreKeptData() {
        if (sharedViewModel.savedProductToAdd.value != null){
            binding.amountTypeInput.setText(sharedViewModel.savedProductToAdd.value?.amount_type)
            binding.currencyInput.setText(sharedViewModel.savedProductToAdd.value?.price_type)
            binding.contactNameInput.setText(MarketPlaceApplication.username)
            binding.isActivatedIndicator.isChecked = true
            binding.pricePerAmountInput.setText(sharedViewModel.savedProductToAdd.value?.price_per_unit)
            binding.availableAmountInput.setText(sharedViewModel.savedProductToAdd.value?.units)
            binding.shortDescriptionInput.setText(sharedViewModel.savedProductToAdd.value?.description)
            binding.titleInput.setText(sharedViewModel.savedProductToAdd.value?.title)
            binding.contactEmailInput.setText(MarketPlaceApplication.user_email)
            binding.phoneNumberInput.setText(MarketPlaceApplication.user_phone_number)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = AddProductViewModelFactory(requireContext(), Repository())
        addProductViewModel = ViewModelProvider(this, factory).get(AddProductViewModel::class.java)
        if (sharedViewModel.UPDATE_PRODUCT_FLAG.value == true){
            val factory = UpdateAssetViewModelFactory(requireContext(), Repository())
            updateAssetViewModel = ViewModelProvider(this, factory).get(UpdateAssetViewModel::class.java)
        }
    }

    private fun settingListener() {
        var everythingFine = true
        binding.titleInput.doOnTextChanged { text, start, before, count ->
            if (text.toString().isEmpty()){
                binding.titleInputLayout.helperText = "This field is required"
                binding.titleInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_error)
                binding.titleInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_error))
                everythingFine = false
            }else {
                binding.titleInputLayout.helperText = ""
                binding.titleInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_color)
                binding.titleInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_color))
                everythingFine = true

            }
        }
        binding.pricePerAmountInput.doOnTextChanged { text, start, before, count ->
            if (text.toString().isEmpty()){
                binding.pricePerAmountInputLayout.helperText = "This field is required"
                binding.pricePerAmountInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_error)
                binding.pricePerAmountInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_error))
                everythingFine = false
            }else {
                binding.pricePerAmountInputLayout.helperText = ""
                binding.pricePerAmountInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_color)
                binding.pricePerAmountInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_color))
                everythingFine = true

            }
        }
        binding.availableAmountInput.doOnTextChanged { text, start, before, count ->
            if (text.toString().isEmpty()){
                binding.availableAmountInputLayout.helperText = "This field is required"
                binding.availableAmountInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_error)
                binding.availableAmountInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_error))
                everythingFine = false
            }else {
                binding.availableAmountInputLayout.helperText = ""
                binding.availableAmountInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_color)
                binding.availableAmountInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_color))
                everythingFine = true

            }

        }
        binding.contactEmailInput.doOnTextChanged { text, start, before, count ->
            if (text.toString().isEmpty()){
                binding.contactEmailLayout.helperText = "This field is required"
                binding.contactEmailLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_error)
                binding.contactEmailLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_error))
                binding.contactEmailLayout.setEndIconTintList(resources.getColorStateList(R.color.text_input_box_stroke_error))
                everythingFine = false
                binding.contactEmailLayout.setEndIconDrawable(R.drawable.ic_error_mark)
            }else {
                binding.contactEmailLayout.helperText = ""
                binding.contactEmailLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_color)
                binding.contactEmailLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_color))
                binding.contactEmailLayout.setEndIconTintList(resources.getColorStateList(R.color.text_input_box_stroke_color))
                binding.contactEmailLayout.setEndIconDrawable(R.drawable.ic_circle_check)
                everythingFine = true

            }
        }
        binding.phoneNumberInput.doOnTextChanged { text, start, before, count ->
            if (text.toString().isEmpty()){
                binding.phoneNumberInputLayout.helperText = "This field is required"
                binding.phoneNumberInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_error)
                binding.phoneNumberInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_error))
                binding.phoneNumberInputLayout.setEndIconTintList(resources.getColorStateList(R.color.text_input_box_stroke_error))
                everythingFine = false
                binding.phoneNumberInputLayout.setEndIconDrawable(R.drawable.ic_error_mark)

            }else {
                binding.phoneNumberInputLayout.helperText = ""
                binding.phoneNumberInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_color)
                binding.phoneNumberInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_color))
                binding.phoneNumberInputLayout.setEndIconTintList(resources.getColorStateList(R.color.text_input_box_stroke_color))
                binding.phoneNumberInputLayout.setEndIconDrawable(R.drawable.ic_circle_check)
                everythingFine = true

            }
        }
        binding.btnLaunchMyFair.setOnClickListener {
            if (binding.titleInput.text.toString().isEmpty()){
                binding.titleInputLayout.helperText = "This field is required"
                binding.titleInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_error)
                binding.titleInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_error))
                everythingFine = false
            }else {
                binding.titleInputLayout.helperText = ""
                binding.titleInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_color)
                binding.titleInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_color))
                everythingFine = true

            }
            if (binding.pricePerAmountInput.text.toString().isEmpty()){
                binding.pricePerAmountInputLayout.helperText = "This field is required"
                binding.pricePerAmountInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_error)
                binding.pricePerAmountInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_error))
                everythingFine = false
            }else {
                binding.pricePerAmountInputLayout.helperText = ""
                binding.pricePerAmountInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_color)
                binding.pricePerAmountInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_color))
                everythingFine = true

            }
            if (binding.availableAmountInput.text.toString().isEmpty()){
                binding.availableAmountInputLayout.helperText = "This field is required"
                binding.availableAmountInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_error)
                binding.availableAmountInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_error))
                everythingFine = false
            }else {
                binding.availableAmountInputLayout.helperText = ""
                binding.availableAmountInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_color)
                binding.availableAmountInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_color))
                everythingFine = true

            }
            if (binding.contactEmailInput.text.toString().isEmpty()){
                binding.contactEmailLayout.helperText = "This field is required"
                binding.contactEmailLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_error)
                binding.contactEmailLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_error))
                everythingFine = false
            }else {
                binding.contactEmailLayout.helperText = ""
                binding.contactEmailLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_color)
                binding.contactEmailLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_color))
                everythingFine = true

            }
            if (binding.phoneNumberInput.text.toString().isEmpty()){
                binding.phoneNumberInputLayout.helperText = "This field is required"
                binding.phoneNumberInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_error)
                binding.phoneNumberInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_error))
                everythingFine = false
            }else {
                binding.phoneNumberInputLayout.helperText = ""
                binding.phoneNumberInputLayout.hintTextColor = context?.resources?.getColorStateList(R.color.text_input_box_stroke_color)
                binding.phoneNumberInputLayout.setBoxStrokeColorStateList(resources.getColorStateList(R.color.text_input_box_stroke_color))
                everythingFine = true

            }
            if (everythingFine){
                initialize()
                showDefaultDialog()
                everythingFine = true
            }

        }
        binding.btnPreviewMyFair.setOnClickListener {
            sharedViewModel.saveDetailsProduct(
                Product(
                0.0,
                    binding.amountTypeInput.text.toString(),
                    binding.currencyInput.text.toString(),
                    "",
                    MarketPlaceApplication.username,
                    binding.isActivatedIndicator.isChecked,
                    binding.pricePerAmountInput.text.toString(),
                    binding.availableAmountInput.text.toString(),
                    binding.shortDescriptionInput.text.toString(),
                    binding.titleInput.text.toString(),
                    listOf<Image>(),
                    System.currentTimeMillis()
                )
            )
            findNavController().navigate(R.id.productDetailsFragment)
        }

        binding.isActivatedIndicator.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                binding.isActivatedIndicatorText.setText("active")
                binding.isActivatedIndicatorText.setTextColor(resources.getColor(R.color.text_input_box_stroke_color))
            }else{
                binding.isActivatedIndicatorText.setText("inactive")
                binding.isActivatedIndicatorText.setTextColor(resources.getColor(R.color.wrapedWhite))
            }

        }
        binding.isActivatedIndicator.isChecked = true
        binding.btnPreviewMyFair.alpha = 0f
        binding.btnPreviewMyFair.animate().alpha(1f).setDuration(1500)
        binding.btnLaunchMyFair.alpha = 0f
        binding.btnLaunchMyFair.animate().alpha(1f).setDuration(1500)
    }


    private fun initialize():Boolean {
        val imageSet = listOf<Image>()
        addProductViewModel.product.value?.uploadImages = imageSet
        addProductViewModel.product.value?.is_active = binding.isActivatedIndicator.isChecked
        addProductViewModel.product.value?.title = binding.titleInput.text.toString()
        addProductViewModel.product.value?.price_per_unit = binding.pricePerAmountInput.text.toString()
        addProductViewModel.product.value?.price_type = binding.currencyInput.text.toString()
        addProductViewModel.product.value?.units = binding.availableAmountInput.text.toString()
        addProductViewModel.product.value?.amount_type = binding.amountTypeInput.text.toString()
        addProductViewModel.product.value?.description = binding.shortDescriptionInput.text.toString()
        addProductViewModel.product.value?.username = MarketPlaceApplication.username.toString()

        Log.d("xxx", "Product has been initialized: ${addProductViewModel.product.value}")
        return true
    }

    private fun showDefaultDialog() {
        val alertDialog = AlertDialog.Builder(this.context)

        alertDialog.apply {
            setIcon(R.drawable.b_icon)
            setTitle("Hello")
            setMessage("You will add a product to your market.Are you sure?")
            setPositiveButton("Yes") { _, _ ->
                if (sharedViewModel.UPDATE_PRODUCT_FLAG.value == true){
                    lifecycleScope.launch {
                        Log.d("product_id", "${sharedViewModel.detailsProduct.value!!.product_id}")
                        updateAssetViewModel.updateProduct(sharedViewModel.detailsProduct.value!!.product_id)
                    }
                }else{
                    lifecycleScope.launch {
                        addProductViewModel.addProduct()
                    }
                }
                findNavController().navigate(R.id.action_addProductToMyMarketFragment_to_marketPlaceFragment)
            }
            setNegativeButton("No") { _, _ ->
                toast("Create an other!")
            }
            setNeutralButton("Cancel") { _, _ ->
                toast("You have to decide something...")
            }
        }.create().show()
    }
    private fun toast(text: String) = Toast.makeText(this.context, text, Toast.LENGTH_SHORT).show()
    override fun onPause() {
        super.onPause()
        val temp = Product(
            0.0,
            binding.amountTypeInput.text.toString(),
            binding.currencyInput.text.toString(),
            "",
            MarketPlaceApplication.username,
            binding.isActivatedIndicator.isChecked,
            binding.pricePerAmountInput.text.toString(),
            binding.availableAmountInput.text.toString(),
            binding.shortDescriptionInput.text.toString(),
            binding.titleInput.text.toString(),
            listOf<Image>(),
            System.currentTimeMillis()
        )
        sharedViewModel.keepProductToEdit(temp)
    }
    private fun handleThatBackPress(){
        val callback: OnBackPressedCallback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                AlertDialog.Builder(requireActivity())
                    .setTitle("Exit!")
                    .setMessage("Are you sure about that? We will keep your data...")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("OK"){ _,_ ->
                        sharedViewModel.saveDetailsProduct(
                            Product(
                                0.0,
                                binding.amountTypeInput.text.toString(),
                                binding.currencyInput.text.toString(),
                                "",
                                MarketPlaceApplication.username,
                                binding.isActivatedIndicator.isChecked,
                                binding.pricePerAmountInput.text.toString(),
                                binding.availableAmountInput.text.toString(),
                                binding.shortDescriptionInput.text.toString(),
                                binding.titleInput.text.toString(),
                                listOf<Image>(),
                                System.currentTimeMillis()
                            )
                        )
                        findNavController().navigate(R.id.marketPlaceFragment)
                    }
                    .show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}