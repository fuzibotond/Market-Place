package com.example.market_place.fragments.market

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.market_place.R
import com.example.market_place.adapter.DataAdapter
import com.example.market_place.databinding.FragmentMyMarketBinding
import com.example.market_place.model.Product
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.ListViewModel
import com.example.market_place.viewmodels.ListViewModelFactory
import com.example.market_place.viewmodels.SharedViewModel
import kotlin.collections.ArrayList


class MyMarketFragment : Fragment(), DataAdapter.OnItemClickListener,
    DataAdapter.OnItemLongClickListener {
    private var _binding: FragmentMyMarketBinding? = null
    private val binding get() = _binding!!
    var itemList: ArrayList<Product> = arrayListOf()
    lateinit var adapter: DataAdapter
    lateinit var listViewModel: ListViewModel
    lateinit var recycler_view: RecyclerView
    private val sharedViewModel:SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyMarketBinding.inflate(inflater, container, false)
        initialize()
        settingListeners()

        return binding.root
    }

    private fun initialize() {
        Log.d("xxx", sharedViewModel.getAllMyproducts().toString())
        sharedViewModel.getAllMyproducts()?.forEach { itemList.add(it) }
        adapter = DataAdapter(itemList,this.requireContext(),this, this)

        recycler_view = binding.myMarketRecyclerView
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)
    }

    private fun settingListeners() {
        binding.btnAddNewProduct.setOnClickListener {
            findNavController().navigate(R.id.action_myMarketFragment_to_addProductToMyMarketFragment)
        }
    }

    override fun onItemClick(position: Int) {
        sharedViewModel.saveDetailsProduct(itemList.get(position))
        Log.d("xxx", "Marketplace:${sharedViewModel.getProduct()}")

        findNavController().navigate(R.id.action_marketPlaceFragment_to_productDetailsFragment)
    }

    override fun onItemLongClick(position: Int) {
        TODO("Not yet implemented")
    }


}