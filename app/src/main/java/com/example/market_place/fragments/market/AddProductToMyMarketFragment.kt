package com.example.market_place.fragments.market


import android.os.Bundle
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
import com.example.market_place.R
import com.example.market_place.databinding.FragmentAddProductToMyMarketBinding
import com.example.market_place.model.AddProductRequest
import com.example.market_place.model.Image
import com.example.market_place.model.Product
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
        initialize()
        settingListener()
        return binding.root
    }

    private fun settingListener() {
        binding.btnLaunchMyFair.setOnClickListener {
            if (addProductViewModel.product.value != null){
                lifecycleScope.launch {
                    addProductViewModel.addProduct()
                }
                sharedViewModel.addProducttoMyMarket(addProductViewModel.registratedProduct.value!!)
            }else{
                Toast.makeText(requireContext(), "Make sure all of fields are filled properly!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnLaunchMyFair.setOnClickListener {
            findNavController().navigate(R.id.action_addProductToMyMarketFragment_to_productDetailsFragment)
        }
    }

    val imageSet = listOf<Image>()
    private fun initialize() {
        addProductViewModel.product.value = AddProductRequest(
            imageSet,
            binding.titleInput.text.toString(),
            binding.shortDescriptionInput.text.toString(),
            binding.pricePerAmountInput.text.toString(),
            binding.availableAmountInput.text.toString(),
            binding.isActivatedIndicator.isChecked,
            0.0,
            binding.amountTypeInput.text.toString(),
            binding.currencyInput.text.toString()
        )
    }


}