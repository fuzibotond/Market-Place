package com.example.market_place.fragments.message

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.market_place.R
import com.example.market_place.adapter.DataAdapter
import com.example.market_place.adapter.MassageAdapter
import com.example.market_place.adapter.OrdersAdapter
import com.example.market_place.databinding.CustomDialogFragmentBinding
import com.example.market_place.databinding.FragmentMarketPlaceBinding
import com.example.market_place.databinding.FragmentShowMessagesBinding
import com.example.market_place.model.Message
import com.example.market_place.model.Order
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ShowMessagesFragment(
    private val username:String,
    private val item_name:String,
    private val messages:List<Message>
): DialogFragment(), DataAdapter.OnItemClickListener, DataAdapter.OnItemLongClickListener {
    private var _binding: FragmentShowMessagesBinding? = null
    private val binding get() = _binding!!
    lateinit var listMessageViewModel: ListMessagesViewModel
    val sharedViewModel:SharedViewModel by activityViewModels()
    lateinit var adapter: MassageAdapter
    val itemList: ArrayList<Message> = arrayListOf()
    lateinit var recycler_view: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.filter_icon);
        _binding = FragmentShowMessagesBinding.inflate(inflater, container, false)
        handleThatBackPress()
        initialize()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factoryOrder = ListMessagesViewModelFactory( Repository())
        listMessageViewModel = ViewModelProvider(this, factoryOrder).get(ListMessagesViewModel::class.java)
    }

    @SuppressLint("ResourceAsColor")
    private fun initialize() {

        binding.dialogMyOrder.setText("Messages for "+item_name+": ")
        messages.forEach {
            Log.d("xxx", "My message "+ it)
            itemList.add(it)
        }
        adapter = MassageAdapter(itemList ,this.requireContext(),this, this, listMessageViewModel,sharedViewModel )

        recycler_view = binding.messagesForProducts
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)
        binding.dialogMyOrder.setOnClickListener {
            dialog?.dismiss()
        }
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

    override fun onItemClick(position: Int) {
        Toast.makeText(requireContext(),    "${messages.get(position)}", Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(position: Int) {
        Toast.makeText(requireContext(),    "${messages.get(position)}", Toast.LENGTH_SHORT).show()
    }
}