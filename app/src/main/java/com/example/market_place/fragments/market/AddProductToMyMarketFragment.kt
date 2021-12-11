package com.example.market_place.fragments.market


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.R
import com.example.market_place.databinding.FragmentAddProductToMyMarketBinding
import com.example.market_place.model.AddProductRequest
import com.example.market_place.model.Image
import com.example.market_place.model.Product
import com.example.market_place.model.ProductHelper
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.AddProductViewModel
import com.example.market_place.viewmodels.AddProductViewModelFactory
import com.example.market_place.viewmodels.LoginViewModel
import com.example.market_place.viewmodels.SharedViewModel
import kotlinx.coroutines.launch
class AddProductToMyMarketFragment : Fragment(){

    private var _binding: FragmentAddProductToMyMarketBinding? = null
    private val binding get() = _binding!!
    lateinit var addProductViewModel:AddProductViewModel
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
    }

    private fun settingListener() {
        binding.btnLaunchMyFair.setOnClickListener {
            initialize()
            lifecycleScope.launch {
                addProductViewModel.addProduct()
            }
            showDefaultDialog()

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
            setMessage("I just wanted to greet you. I hope you are doing great!")
            setPositiveButton("Yes") { _, _ ->
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
}