package com.example.market_place.fragments.market

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.adapter.DataAdapter
import com.example.market_place.adapter.DataAdapterForOrders
import com.example.market_place.databinding.FragmentOngoingOrdersBinding
import com.example.market_place.model.Order
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.ListOrderViewModel
import com.example.market_place.viewmodels.ListOrderViewModelFactory
import com.example.market_place.viewmodels.ListViewModelFactory
import com.example.market_place.viewmodels.SharedViewModel
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList


class OngoingOrdersFragment : Fragment(), DataAdapterForOrders.OnItemClickListener,
    DataAdapterForOrders.OnItemLongClickListener {
    lateinit var adapter: DataAdapterForOrders
    lateinit var listOrderViewModel: ListOrderViewModel
    lateinit var recycler_view: RecyclerView
    lateinit var spinner: Spinner
    private var _binding: FragmentOngoingOrdersBinding? = null
    private val binding get() = _binding!!
    val itemList: ArrayList<Order> = arrayListOf()
    val sharedViewModel: SharedViewModel by activityViewModels()

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
        val factory = ListOrderViewModelFactory( Repository())
        listOrderViewModel = ViewModelProvider(this, factory).get(ListOrderViewModel::class.java)
        lifecycleScope.launch {
            listOrderViewModel.getOrders()
        }
    }

    private fun intitialze() {


        listOrderViewModel.orders.observe(viewLifecycleOwner){
            listOrderViewModel.orders.value?.forEach{
                itemList.add(it)
                Log.d("xxx", "${it.username} We are on ! Saveing my market items...${MarketPlaceApplication.username}")
//                if (it.username == MarketPlaceApplication.username){
//                    sharedViewModel.addProducttoMyMarket(it)
//
//                }
            }
            adapter.setData(itemList)
            adapter.notifyDataSetChanged()

        }
        adapter = DataAdapterForOrders(itemList,this.requireContext(),this, this)

        recycler_view = binding.myFaresRecyclerView
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onItemLongClick(position: Int) {
        TODO("Not yet implemented")
    }

}