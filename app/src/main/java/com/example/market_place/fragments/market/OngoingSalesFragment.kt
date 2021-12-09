package com.example.market_place.fragments.market

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private fun initialize() {
        listOrderViewModel.orders.observe(viewLifecycleOwner){
            Log.d("xxx", "Orders: "+ listOrderViewModel.orders.value)
//
            listOrderViewModel.orders.value!!.forEach {
                itemList.add(it)
            }
            sharedViewModel.saveOrders(itemList)
            adapter.setData(itemList)
            adapter.notifyDataSetChanged()
        }
        adapter = SalesAdapter(itemList ,this.requireContext(),this, this)

        recycler_view = binding.myFaresRecyclerView
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)

    }

    override fun onItemClick(position: Int) {
      Log.d("xxx", "Clicked")
    }

    override fun onItemLongClick(position: Int) {
        Log.d("xxx", "LongClicked")
    }
}