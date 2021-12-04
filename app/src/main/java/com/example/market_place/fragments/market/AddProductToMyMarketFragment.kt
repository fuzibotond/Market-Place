package com.example.market_place.fragments.market


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.market_place.databinding.FragmentAddProductToMyMarketBinding

class AddProductToMyMarketFragment : Fragment(){

    private var _binding: FragmentAddProductToMyMarketBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddProductToMyMarketBinding.inflate(
            inflater, container, false)

        return binding.root
    }



}