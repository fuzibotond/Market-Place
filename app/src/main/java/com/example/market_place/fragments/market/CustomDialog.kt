package com.example.market_place.fragments.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.market_place.R
import com.example.market_place.databinding.CustomDialogFragmentBinding
import com.example.market_place.databinding.FragmentMarketPlaceBinding
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.AddOrderViewModel
import com.example.market_place.viewmodels.AddOrderViewModelFactory
import com.example.market_place.viewmodels.SharedViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CustomDialog: DialogFragment() {
    private var _binding: CustomDialogFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var addOrderViewModel:AddOrderViewModel
    val sharedViewModel:SharedViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.filter_icon);
        _binding = CustomDialogFragmentBinding.inflate(inflater, container, false)
        initialize()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factoryOrder = AddOrderViewModelFactory(this.requireContext(), Repository())
        addOrderViewModel = ViewModelProvider(this, factoryOrder).get(AddOrderViewModel::class.java)
    }

    private fun initialize() {
        binding.btnSendMyOrder.setOnClickListener {
            addOrderViewModel.order.value?.title  = sharedViewModel.orderToAdd.value?.title
            addOrderViewModel.order.value?.description = sharedViewModel.orderToAdd.value?.description
            addOrderViewModel.order.value?.owner_username  = sharedViewModel.orderToAdd.value?.owner_username
            addOrderViewModel.order.value?.units  = sharedViewModel.orderToAdd.value?.units
            addOrderViewModel.order.value?.price_per_unit  = sharedViewModel.orderToAdd.value?.price_per_unit
            addOrderViewModel.order.value?.uploadImages  = listOf()

            GlobalScope.launch {
                addOrderViewModel.addOrder()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}