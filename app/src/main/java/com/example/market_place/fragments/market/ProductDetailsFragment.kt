package com.example.market_place.fragments.market

import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.market_place.R
import com.example.market_place.databinding.FragmentMarketPlaceBinding
import com.example.market_place.databinding.FragmentProductDetailsBinding
import com.example.market_place.viewmodels.SharedViewModel
import com.site_valley.imagesliderexampleinkotlin.MySliderImageAdapter
import com.smarteist.autoimageslider.SliderView

class ProductDetailsFragment : Fragment() {
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        initialize()
        settingListener()
        return binding.root
    }

    private fun settingListener() {
        binding.productDetailsEdit.setOnClickListener {
            findNavController().navigate(R.id.action_productDetailsFragment_to_marketPlaceFragment)
        }
    }

    private fun initialize() {
        val currentProduct = sharedViewModel.getProduct()
        Log.d("xxx", "Details:${currentProduct}")
//        val sliderArrayList = sharedViewModel.getDetailsProduct()?.images
        val imageList: ArrayList<String> = ArrayList()
//        sliderArrayList?.forEach { imageList.add(it.image_path) }
        imageList.add("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg")
        imageList.add("https://images.ctfassets.net/hrltx12pl8hq/4plHDVeTkWuFMihxQnzBSb/aea2f06d675c3d710d095306e377382f/shutterstock_554314555_copy.jpg")
        imageList.add("https://media.istockphoto.com/photos/child-hands-formig-heart-shape-picture-id951945718?k=6&m=951945718&s=612x612&w=0&h=ih-N7RytxrTfhDyvyTQCA5q5xKoJToKSYgdsJ_mHrv0=")
        setImageInSlider(imageList, binding.imageSlider)
        binding.productDetailsProfileName.text = currentProduct?.username
        binding.productDetailsTitle.text = currentProduct?.title
//        TODO binding.productDetailsProfileImage
        if (currentProduct?.is_active == true){
            binding.productDetailsActive.text = "active"
            binding.productDetailsActive.setTextColor(resources.getColorStateList(R.color.text_input_box_stroke_color))
        }else{
            binding.productDetailsActive.text = "inactive"
            binding.productDetailsActive.setTextColor(resources.getColorStateList(R.color.wrapedWhite))
        }
        binding.productDetailsDescription.text = currentProduct?.description
        binding.productDetailsDate.text = currentProduct?.creation_time.toString()
        binding.productDetailsPriceAndUnit.text = currentProduct?.price_per_unit + currentProduct?.price_type+"/"+ currentProduct?.amount_type
        binding.productDetailsReview.text = "0"
        binding.productDetailsSoldItems.text = "0"
        binding.productDetailsTotalItems.text = currentProduct?.units+" "+ currentProduct?.amount_type
        binding.productDetailsPricePerItem.text = currentProduct?.price_per_unit +" "+currentProduct?.price_type
    }

    private fun setImageInSlider(images: ArrayList<String>, imageSlider: SliderView) {
        val adapter = MySliderImageAdapter()
        adapter.renewItems(images)
        imageSlider.setSliderAdapter(adapter)
        imageSlider.isAutoCycle = true
        imageSlider.startAutoCycle()
    }

}