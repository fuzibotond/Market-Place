package com.example.market_place.fragments.market


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddProductToMyMarketBinding.inflate(
            inflater, container, false)
        val factory = AddProductViewModelFactory(requireContext(), Repository())
        addProductViewModel = ViewModelProvider(this, factory).get(AddProductViewModel::class.java)
        settingListener()
        return binding.root
    }

    private fun settingListener() {
        binding.btnLaunchMyFair.setOnClickListener {
            initialize()
            if (addProductViewModel.product.value != null){

                lifecycleScope.launch {
                    addProductViewModel.addProduct()
                }
//                sharedViewModel.addProducttoMyMarket(addProductViewModel.registratedProduct.value!!)
                Log.d("xxx", "Product added successfully ${addProductViewModel.product.value}")

            }else{
                Log.d("xxx", "unable to add product${addProductViewModel.product.value}")
                Toast.makeText(requireActivity(), "Make sure all of fields are filled properly!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnPreviewMyFair.setOnClickListener {
            findNavController().navigate(R.id.action_addProductToMyMarketFragment_to_productDetailsFragment)
        }
    }


    private fun initialize() {
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
    }


}