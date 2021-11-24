package com.example.market_place.fragments.market

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.market_place.R
import com.example.market_place.adapter.DataAdapter
import com.example.market_place.model.Product
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.ListViewModel
import com.example.market_place.viewmodels.ListViewModelFactory

class MarketPlaceFragment : Fragment(), DataAdapter.OnItemClickListener,
    DataAdapter.OnItemLongClickListener {

    lateinit var viewLayout: View
    lateinit var adapter:DataAdapter
    lateinit var listViewModel:ListViewModel
    lateinit var recycler_view:RecyclerView
    lateinit var spinner:Spinner
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLayout = inflater.inflate(R.layout.fragment_market_place, container, false)
        val itemList: ArrayList<Product> = arrayListOf()
        listViewModel.products.observe(viewLifecycleOwner){
            listViewModel.products.value?.forEach{
                itemList.add(it)
                Log.d("list", itemList.size.toString())
            }
            adapter.setData(itemList)
            adapter.notifyDataSetChanged()
        }
        spinner = viewLayout.findViewById<Spinner>(R.id.seller_spinner)
        spinner?.adapter = activity?.let { ArrayAdapter(it.applicationContext, R.layout.support_simple_spinner_dropdown_item,itemList ) } as SpinnerAdapter
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("erreur")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val type = parent?.getItemAtPosition(position).toString()
                itemList.removeAll{true}
//                itemList.addAll(listt)
                adapter.notifyDataSetChanged()

            }
        }


        adapter = DataAdapter(itemList,this.requireActivity(),this, this)

        recycler_view = viewLayout.findViewById(R.id.recycler_view)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)
        return viewLayout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ListViewModelFactory( Repository())
        listViewModel = ViewModelProvider(this, factory).get(ListViewModel::class.java)
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onItemLongClick(position: Int) {
        TODO("Not yet implemented")
    }
}