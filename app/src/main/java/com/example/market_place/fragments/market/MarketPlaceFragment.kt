package com.example.market_place.fragments.market

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
import com.example.market_place.model.Product
import com.example.market_place.repository.Repository
import com.example.market_place.databinding.FragmentMarketPlaceBinding
import com.example.market_place.viewmodels.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log


class MarketPlaceFragment : Fragment(), DataAdapter.OnItemClickListener,
    DataAdapter.OnItemLongClickListener {
    val sharedViewModel:SharedViewModel by activityViewModels()
    lateinit var viewLayout: View
    lateinit var adapter:DataAdapter
    lateinit var listViewModel:ListViewModel
    lateinit var addOrderViewModel:AddOrderViewModel
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
        handleThatBackPress()
        fillList()
        settingListeners()
        return binding.root
    }



    private fun settingListeners() {
        binding.switcher.setOnCheckedChangeListener { button, b ->
            if (b){
                itemList.sortBy { it.creation_time }
                itemList.reverse()
            }
            adapter.notifyDataSetChanged()
        }
        sharedViewModel.searchingKeyword.observe(viewLifecycleOwner){
            val searchResultList = arrayListOf<Product>()
            itemList.forEach {
                if (it.title.contains(sharedViewModel.searchingKeyword.value!!, ignoreCase = true)){
                    searchResultList.add(it)
                }
            }
            adapter.setData(searchResultList)
            adapter.notifyDataSetChanged()
        }
        adapter = DataAdapter(itemList,this.requireContext(),this, this,addOrderViewModel, sharedViewModel)

        recycler_view = binding.recyclerView
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)

    }

    private fun fillList() {
        listViewModel.products.observe(viewLifecycleOwner){
            val tempList = mutableListOf<Product>()

            listViewModel.products.value?.forEach{
                val temp = Product(
                    it.rating,
                    it.amount_type.replace("\"", ""),
                    it.price_type.replace("\"", ""),
                    it.product_id.replace("\"", ""),
                    it.username.replace("\"", ""),
                    it.is_active,
                    it.price_per_unit.replace("\"", ""),
                    it.units.replace("\"", ""),
                    it.description.replace("\"", ""),
                    it.title.replace("\"", ""),
                    it.images,
                    it.creation_time
                )
                itemList.add(temp)
                if (it.username.replace("\"", "") == MarketPlaceApplication.username){
                    tempList.add(temp)
                }
            }
            binding.progressBar.visibility = View.GONE
            adapter.setData(itemList)
            binding.countItem.text = itemList.size.toString() + " Fairs"
            val date = Date(System.currentTimeMillis())
            val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
            binding.dateOfList.text = format.format(date)
            sharedViewModel.addProducttoMyMarket(tempList as List<Product>)
            adapter.notifyDataSetChanged()

        }


        binding.sellerSpinner?.adapter = activity?.let { ArrayAdapter(it.applicationContext, R.layout.dropdown_filter,itemCategoryNameList ) } as SpinnerAdapter
        binding.sellerSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                    val temp = itemList.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it.username }))
                    itemList.clear()
                    itemList.addAll(temp)
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
        val factoryOrder = AddOrderViewModelFactory(this.requireContext(), Repository())
        addOrderViewModel = ViewModelProvider(this, factoryOrder).get(AddOrderViewModel::class.java)

    }

    override fun onItemClick(position: Int) {
        if (itemList.get(position).username == MarketPlaceApplication.username){
            sharedViewModel.setStateIfUpdateable(true)
            sharedViewModel.saveDetailsProduct(itemList.get(position))
            findNavController().navigate(R.id.action_marketPlaceFragment_to_productDetailsFragment)
        }else{
            sharedViewModel.setStateIfUpdateable(false)
            sharedViewModel.saveDetailsProduct(itemList.get(position))
            findNavController().navigate(R.id.productDetailsForCustomers)
        }

    }

    override fun onItemLongClick(position: Int) {
        findNavController().navigate(R.id.productDetailsForCustomers)
    }

    private fun clearDate() {
        val sharedPreferences:SharedPreferences = requireContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().commit()

    }
    private fun handleThatBackPress(){
        val callback: OnBackPressedCallback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                AlertDialog.Builder(requireActivity())
                    .setTitle("Exit!")
                    .setMessage("Are you sure about that?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("OK"){ _,_ ->
                        clearDate()
                        requireActivity().finish()
                    }
                    .show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}