package com.example.market_place.fragments.market

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.market_place.R
import com.example.market_place.databinding.CustomDialogFragmentBinding
import com.example.market_place.databinding.FragmentMarketPlaceBinding
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.AddOrderViewModel
import com.example.market_place.viewmodels.AddOrderViewModelFactory
import com.example.market_place.viewmodels.SharedViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CustomDialog(
    private val username:String,
    private val item_name:String,
    private val price:String,
    private val is_active:Boolean,
    private val creation_time:Long,
    private val available_amount: String,
): DialogFragment() {
    private var _binding: CustomDialogFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var addOrderViewModel:AddOrderViewModel
    val sharedViewModel:SharedViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.filter_icon);
        _binding = CustomDialogFragmentBinding.inflate(inflater, container, false)
        handleThatBackPress()
        initialize()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factoryOrder = AddOrderViewModelFactory(this.requireContext(), Repository())
        addOrderViewModel = ViewModelProvider(this, factoryOrder).get(AddOrderViewModel::class.java)
    }

    @SuppressLint("ResourceAsColor")
    private fun initialize() {

        binding.dialogProfileName.setText(username)
        binding.dialogPrice.setText(price)
        binding.dialogAmountInput.setText(available_amount)
        if (is_active) {
            binding.dialogIsActive.setText("active")
        }else{
            binding.dialogIsActive.setText("inactive")
            binding.dialogIsActive.setTextColor(R.color.teal_primary)
        }
        binding.dialogItemName.setText(item_name)
        val date = Date(creation_time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        binding.dialogDateTime.setText(format.format(date))

        binding.btnSendMyOrder.setOnClickListener {
            addOrderViewModel.order.value?.title  = sharedViewModel.orderToAdd.value?.title
            addOrderViewModel.order.value?.description = binding.dialogComments.text.toString()
            addOrderViewModel.order.value?.owner_username  = sharedViewModel.orderToAdd.value?.owner_username
            val available_amount = sharedViewModel.orderToAdd.value?.units?.toInt()
            val asked_for = binding.dialogAmountInput.text.toString().toInt()
            if(available_amount!! -asked_for>=0){
                addOrderViewModel.order.value?.units  = binding.dialogAmountInput.text.toString()
            }else{
                Toast.makeText(this.requireContext(), "Too much! You have to order less than ${binding.dialogAmountInput.text.toString()}", Toast.LENGTH_SHORT).show()
            }
            addOrderViewModel.order.value?.price_per_unit  = sharedViewModel.orderToAdd.value?.price_per_unit
            addOrderViewModel.order.value?.uploadImages  = listOf()

            GlobalScope.launch {
                addOrderViewModel.addOrder()
            }
            Toast.makeText(context,"You order is successfully processed" , Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }
        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
        binding.dialogMyOrder.setOnClickListener {
            dialog?.dismiss()
        }
        binding.btnSendMyOrder.alpha = 0f
        binding.btnSendMyOrder.animate().alpha(1f).setDuration(1500)
        binding.btnCancel.alpha = 0f
        binding.btnCancel.animate().alpha(1f).setDuration(1500)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    private fun handleThatBackPress(){
        val callback: OnBackPressedCallback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.addProductToMyMarketFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}