package com.example.market_place.fragments.market

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.R
import com.example.market_place.adapter.DataAdapter
import com.example.market_place.adapter.SalesAdapter
import com.example.market_place.databinding.FragmentMyMarketBinding
import com.example.market_place.databinding.FragmentOngoingSalesBinding
import com.example.market_place.model.Order
import com.example.market_place.model.Product
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.ListOrderViewModel
import com.example.market_place.viewmodels.ListOrderViewModelFactory
import com.example.market_place.viewmodels.ListViewModel
import com.example.market_place.viewmodels.SharedViewModel


class OngoingSalesFragment : Fragment(), DataAdapter.OnItemClickListener,
    DataAdapter.OnItemLongClickListener, SalesAdapter.OnItemClickListener,
    SalesAdapter.OnItemLongClickListener {
    private var _binding: FragmentOngoingSalesBinding? = null
    private val binding get() = _binding!!
    var itemList: ArrayList<Order> = arrayListOf()
    lateinit var adapter: SalesAdapter
    lateinit var recycler_view: RecyclerView
    private val sharedViewModel: SharedViewModel by activityViewModels()
    lateinit var listOrderViewModel:ListOrderViewModel
    var new_item:Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOngoingSalesBinding.inflate(inflater, container, false)
        initialize()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ListOrderViewModelFactory( Repository())
        listOrderViewModel = ViewModelProvider(this, factory).get(ListOrderViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
    private fun initialize() {
        listOrderViewModel.orders.observe(viewLifecycleOwner){
            Log.d("xxx", "Orders: "+ listOrderViewModel.orders.value)
            saveItemData()
            listOrderViewModel.orders.value!!.forEach {
                if(it.owner_username==MarketPlaceApplication.username){
                    itemList.add(it)
                }
            }
            sharedViewModel.saveOrders(itemList)
            sharedViewModel.saveOrderItemCount(sharedViewModel.orders.value!!.size)
            adapter.setData(itemList)
            loadData()
            adapter.notifyDataSetChanged()
            saveItemData()
        }

        adapter = SalesAdapter(itemList ,this.requireContext(),this, this)

        recycler_view = binding.myFaresRecyclerView
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)

    }

    override fun onItemClick(position: Int) {
        TODO("")
    }

    override fun onItemLongClick(position: Int) {
        Log.d("xxx", "LongClicked")
    }
    private fun saveItemData(){
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            listOrderViewModel.item_count.value?.let { putInt("SALES_ITEM_COUNT_KEY", it) }
        }.apply()
    }
    private fun loadData() {
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedItemCount = sharedPreferences.getInt("SALES_ITEM_COUNT_KEY", 0)
        if (savedItemCount < itemList.size ){
            binding.myFaresCountItem.text = (itemList.size-savedItemCount).toString() + " New item"
        }else{
            binding.myFaresCountItem.text = "0 New item"
            Toast.makeText(requireContext(), "Ain't no new item :((", Toast.LENGTH_SHORT).show()
        }


    }
    private fun orderToProduct(order: Order):Product{
        return Product(0.0, "unit", "Ron", order.order_id.toString(), order.owner_username, true, order.price_per_unit, order.units, order.description, order.title,order.images,order.creation_time)
    }
}