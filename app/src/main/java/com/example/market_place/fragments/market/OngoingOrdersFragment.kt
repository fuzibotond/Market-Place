package com.example.market_place.fragments.market

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.adapter.DataAdapter
import com.example.market_place.adapter.SalesAdapter
import com.example.market_place.databinding.FragmentOngoingOrdersBinding
import com.example.market_place.model.Order
import com.example.market_place.model.Product
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.ListOrderViewModel
import com.example.market_place.viewmodels.ListOrderViewModelFactory
import com.example.market_place.viewmodels.ListViewModelFactory
import com.example.market_place.viewmodels.SharedViewModel
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList


class OngoingOrdersFragment : Fragment(), SalesAdapter.OnItemClickListener,
    SalesAdapter.OnItemLongClickListener {
    lateinit var adapter: SalesAdapter
    lateinit var listOrderViewModel: ListOrderViewModel
    lateinit var recycler_view: RecyclerView
    lateinit var spinner: Spinner
    private var _binding: FragmentOngoingOrdersBinding? = null
    private val binding get() = _binding!!
    val itemList: ArrayList<Order> = arrayListOf()
    val sharedViewModel: SharedViewModel by activityViewModels()
    var new_item:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOngoingOrdersBinding.inflate(inflater, container, false)
        intitialze()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
    private fun intitialze() {


       sharedViewModel.orders.observe(viewLifecycleOwner){
            Log.d("xxx", "Orders: "+ sharedViewModel.orders.value)
            sharedViewModel.orders.value!!.forEach { itemList.add(it) }
            adapter.setData(itemList)
            adapter.notifyDataSetChanged()
            saveItemData()
        }
        adapter = SalesAdapter(itemList ,this.requireContext(),this, this, )

        recycler_view = binding.myFaresOrdersRecyclerView
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)

    }

    override fun onItemClick(position: Int) {
        Log.d("xxx", "Clicked")
    }

    override fun onItemLongClick(position: Int) {
        Log.d("xxx", "Long Clicked")
    }
    private fun saveItemData(){
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            sharedViewModel.order_item_count.value?.let { putInt("ORDER_ITEM_COUNT_KEY", it) }
        }.apply()
    }
    private fun loadData() {
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedItemCount = sharedPreferences.getInt("ORDER_ITEM_COUNT_KEY", 0)
        if (savedItemCount < itemList.size ){
            binding.myFaresCountItem.text = (itemList.size-savedItemCount).toString() + " New item"
        }else{
            binding.myFaresCountItem.text = "0 New item"
            Toast.makeText(requireContext(), "Ain't no new item :((", Toast.LENGTH_SHORT).show()
        }


    }

}