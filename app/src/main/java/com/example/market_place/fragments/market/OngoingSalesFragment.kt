package com.example.market_place.fragments.market

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.market_place.R
import com.example.market_place.adapter.DataAdapter
import com.example.market_place.databinding.FragmentMyMarketBinding
import com.example.market_place.databinding.FragmentOngoingSalesBinding
import com.example.market_place.model.Product
import com.example.market_place.viewmodels.ListViewModel
import com.example.market_place.viewmodels.SharedViewModel


class OngoingSalesFragment : Fragment(), DataAdapter.OnItemClickListener,
    DataAdapter.OnItemLongClickListener {
    private var _binding: FragmentOngoingSalesBinding? = null
    private val binding get() = _binding!!
    var itemList: ArrayList<Product> = arrayListOf()
    lateinit var adapter: DataAdapter
    lateinit var recycler_view: RecyclerView
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOngoingSalesBinding.inflate(inflater, container, false)
        initialize()
        return binding.root
    }

    private fun initialize() {
        Log.d("xxx", sharedViewModel.getAllMyproducts().toString())
        sharedViewModel.getAllMyproducts()?.forEach { itemList.add(it) }
        adapter = DataAdapter(itemList,this.requireContext(),this, this)

        recycler_view = binding.myFaresRecyclerView
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)
    }

    override fun onItemClick(position: Int) {
        sharedViewModel.saveDetailsProduct(itemList.get(position))
        Log.d("xxx", "Marketplace:${sharedViewModel.getProduct()}")
    }

    override fun onItemLongClick(position: Int) {
        TODO("Not yet implemented")
    }
}