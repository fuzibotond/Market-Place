package com.example.market_place.fragments.message

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
import com.example.market_place.databinding.FragmentMessageBinding
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MessageFragment(
    private val username:String,
    private val item_name:String,
    private val price:String,
    private val is_active:Boolean,
    private val creation_time:Long,
    private val product_id: String,
    private val isProduct:Boolean
): DialogFragment() {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!
    lateinit var addMessageViewModel:AddMessagesViewModel
    val sharedViewModel:SharedViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.filter_icon);
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        handleThatBackPress()
        initialize()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factoryOrder = AddMessagesViewModelFactory(this.requireContext(), Repository())
        addMessageViewModel = ViewModelProvider(this, factoryOrder).get(AddMessagesViewModel::class.java)
    }

    @SuppressLint("ResourceAsColor")
    private fun initialize() {
        binding.dialogComments.setTextColor(resources?.getColorStateList(R.color.darkGrey))
        binding.dialogProfileName.setText(username)
        binding.dialogPrice.setText(price)
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
            if (isProduct){
                GlobalScope.launch {
                    addMessageViewModel.addMessageToProduct(product_id, binding.dialogComments.text.toString())
                }
            }else{
                GlobalScope.launch {
                    addMessageViewModel.addMessageToOrder(product_id, binding.dialogComments.text.toString())
                }
            }

            Toast.makeText(context,"Your message was sent to ${username}" , Toast.LENGTH_SHORT).show()
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