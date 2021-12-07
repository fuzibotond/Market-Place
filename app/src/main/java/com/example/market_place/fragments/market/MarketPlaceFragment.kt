package com.example.market_place.fragments.market

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.graphics.createBitmap
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.market_place.AuthorizedActivity
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.R
import com.example.market_place.adapter.DataAdapter
import com.example.market_place.model.Product
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.ListViewModel
import com.example.market_place.viewmodels.ListViewModelFactory
import com.example.market_place.databinding.FragmentMarketPlaceBinding
import com.example.market_place.viewmodels.SharedViewModel
import java.util.*
import kotlin.collections.ArrayList


class MarketPlaceFragment : Fragment(), DataAdapter.OnItemClickListener,
    DataAdapter.OnItemLongClickListener {
    val sharedViewModel:SharedViewModel by activityViewModels()
    lateinit var viewLayout: View
    lateinit var adapter:DataAdapter
    lateinit var listViewModel:ListViewModel
    lateinit var recycler_view:RecyclerView
    lateinit var spinner:Spinner
    private var _binding: FragmentMarketPlaceBinding? = null
    private val binding get() = _binding!!
    val itemList: ArrayList<Product> = arrayListOf()
    val itemCategoryNameList: ArrayList<String> = arrayListOf("Time", "Seller","Price")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMarketPlaceBinding.inflate(inflater, container, false)
        fillList()
        settingListeners()
        return binding.root
    }

    override fun onPause() {
        super.onPause()
    }

    private fun settingListeners() {
        binding.switcher.setOnCheckedChangeListener { button, b ->
            if (b){
                itemList.sortBy { it.creation_time }
                itemList.forEach { Log.d("price", it.creation_time.toString()) }
            }else{
                itemList.shuffle()
            }
            adapter.notifyDataSetChanged()
        }

        adapter = DataAdapter(itemList,this.requireContext(),this, this)

        recycler_view = binding.recyclerView
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)


    }

    private fun fillList() {
        listViewModel.products.observe(viewLifecycleOwner){
            listViewModel.products.value?.forEach{
                itemList.add(it)
                Log.d("xxx", "${it.username} We are on ! Saveing my market items...${MarketPlaceApplication.username}")
                if (it.username == MarketPlaceApplication.username){
                    sharedViewModel.addProducttoMyMarket(it)

                }
            }
            adapter.setData(itemList)
            binding.countItem.text = itemList.size.toString()+ " " + "Fairs"
            binding.dateOfList.text = Calendar.getInstance().time.toString()
            adapter.notifyDataSetChanged()

        }

        spinner = binding.sellerSpinner
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            spinner?.tooltipText = itemCategoryNameList.get(0)
//
//        }
        spinner?.gravity = Gravity.CENTER
        spinner?.adapter = activity?.let { ArrayAdapter(it.applicationContext, R.layout.support_simple_spinner_dropdown_item,itemCategoryNameList ) } as SpinnerAdapter
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(requireActivity(), "You not selected any category", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                val type = parent?.getItemAtPosition(position).toString()
                if (type == "Time"){
                    itemList.sortBy { it.creation_time }
                }
                if (type == "Seller"){
                    itemList.sortBy { it.username }
                }
                if (type == "Price"){

                    itemList.sortBy { it.price_per_unit.toInt() }
                }
                adapter.notifyDataSetChanged()

            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ListViewModelFactory( Repository())
        listViewModel = ViewModelProvider(this, factory).get(ListViewModel::class.java)
    }

    override fun onItemClick(position: Int) {
        sharedViewModel.saveDetailsProduct(itemList.get(position))
        Log.d("xxx", "Marketplace:${sharedViewModel.getProduct()}")

        findNavController().navigate(R.id.action_marketPlaceFragment_to_productDetailsFragment)
    }

    override fun onItemLongClick(position: Int) {
        Log.d("xxx", "Marketplace")
        findNavController().navigate(R.id.action_marketPlaceFragment_to_productDetailsFragment)
    }
}