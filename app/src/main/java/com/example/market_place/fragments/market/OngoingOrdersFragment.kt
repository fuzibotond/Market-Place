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
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.R
import com.example.market_place.adapter.DataAdapter
import com.example.market_place.adapter.OrdersAdapter
import com.example.market_place.adapter.SalesAdapter
import com.example.market_place.databinding.FragmentOngoingOrdersBinding
import com.example.market_place.model.Order
import com.example.market_place.model.Product
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.*
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList


class OngoingOrdersFragment : Fragment(), OrdersAdapter.OnItemClickListener,
    OrdersAdapter.OnItemLongClickListener {
    lateinit var adapter: OrdersAdapter
    lateinit var listOrderViewModel: ListOrderViewModel
    lateinit var recycler_view: RecyclerView
    lateinit var spinner: Spinner
    lateinit var updateAssetViewModel:UpdateAssetViewModel
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
        handleThatBackPress()
        intitialze()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factoryUpdate = UpdateAssetViewModelFactory( this.requireActivity(),Repository())
        updateAssetViewModel = ViewModelProvider(this, factoryUpdate).get(UpdateAssetViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
    private fun intitialze() {


       sharedViewModel.orders.observe(viewLifecycleOwner){
            Log.d("xxx", "Orders: "+ sharedViewModel.orders.value)

            sharedViewModel.orders.value!!.forEach {
                if(it.username==MarketPlaceApplication.username){
                    itemList.add(it)
                }
            }
            adapter.setData(itemList)
            adapter.notifyDataSetChanged()
        }

        sharedViewModel.searchingKeyword.observe(viewLifecycleOwner){
            val searchResultList = arrayListOf<Order>()
            itemList.forEach {
                if (it.title.contains(sharedViewModel.searchingKeyword.value!!, ignoreCase = true)){
                    searchResultList.add(it)
                }
            }
            adapter.setData(searchResultList)
            adapter.notifyDataSetChanged()
        }
        adapter = OrdersAdapter(itemList ,this.requireContext(),this, this,sharedViewModel,updateAssetViewModel )

        recycler_view = binding.myFaresOrdersRecyclerView
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)

    }

    override fun onItemClick(position: Int) {
        val temp =orderToProduct(itemList.get(position))
        sharedViewModel.saveDetailsProduct(temp)
        findNavController().navigate(R.id.productDetailsForCustomers)
    }

    override fun onPause() {
        super.onPause()
        saveItemData()
    }
    override fun onItemLongClick(position: Int) {
        val temp =orderToProduct(itemList.get(position))
        sharedViewModel.saveDetailsProduct(temp)
        findNavController().navigate(R.id.productDetailsForCustomers)
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
    private fun orderToProduct(order: Order):Product{
        return Product(0.0, "unit", "Ron", order.order_id.toString(), order.owner_username, true, order.price_per_unit, order.units, order.description, order.title,order.images,order.creation_time)
    }
    private fun handleThatBackPress(){
        val callback: OnBackPressedCallback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.marketPlaceFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}