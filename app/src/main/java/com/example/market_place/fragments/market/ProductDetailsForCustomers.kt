package com.example.market_place.fragments.market

import android.app.AlertDialog
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.R
import com.example.market_place.databinding.FragmentMarketPlaceBinding
import com.example.market_place.databinding.FragmentProductDetailsBinding
import com.example.market_place.databinding.FragmentProductDetailsForCustomersBinding
import com.example.market_place.model.Order
import com.example.market_place.model.Product
import com.example.market_place.repository.Repository
import com.example.market_place.viewmodels.*
import com.site_valley.imagesliderexampleinkotlin.MySliderImageAdapter
import com.smarteist.autoimageslider.SliderView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.handleCoroutineException
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import androidx.core.app.ActivityCompat.startActivityForResult

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.example.market_place.fragments.message.MessageFragment
import com.squareup.picasso.RequestCreator


class ProductDetailsForCustomers : Fragment() {
    private var _binding: FragmentProductDetailsForCustomersBinding? = null
    private val binding get() = _binding!!
    val sharedViewModel: SharedViewModel by activityViewModels()
    lateinit var addOrderViewModel:AddOrderViewModel
    private lateinit var userInfoViewModel: UserInfoViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductDetailsForCustomersBinding.inflate(inflater, container, false)
        handleThatBackPress()
        initialize()
        settingListeners()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factoryOrder = AddOrderViewModelFactory(this.requireContext(), Repository())
        addOrderViewModel = ViewModelProvider(this, factoryOrder).get(AddOrderViewModel::class.java)
        val factoryProfile = UserInfoViewModelFactory( Repository())
        userInfoViewModel = ViewModelProvider(this, factoryProfile).get(UserInfoViewModel::class.java)
    }

    private fun settingListeners() {
        binding.btnOrderThis.setOnClickListener {
            val currentItem = sharedViewModel.detailsProduct.value
            val manager = (context as AppCompatActivity).supportFragmentManager
            if (currentItem!=null){
                val temp = Order(
                    currentItem.username,
                    "",
                    MarketPlaceApplication.username,
                    arrayListOf(),
                    currentItem.price_per_unit,
                    currentItem.units,
                    currentItem.description,
                    currentItem.title,
                    currentItem.images,
                    currentItem.creation_time,
                    ""
                )
                sharedViewModel.saveOrderToAdd(temp)
                CustomDialog(currentItem.username, currentItem.title, currentItem.price_per_unit+" "+currentItem.price_type+"/"+currentItem.amount_type,currentItem.is_active, currentItem.creation_time, currentItem.units).show(manager, "CustomManager")

            }


        }
        binding.btnOrderThis.alpha = 0f
        binding.btnOrderThis.animate().alpha(1f).setDuration(1500)
        binding.btnCallNow.setOnClickListener {
            GlobalScope.launch {

                sharedViewModel.detailsProduct.value?.username?.let { it1 ->
                    userInfoViewModel.getUserInfo(
                        it1
                    )
                }
            }
            userInfoViewModel.user.observe(viewLifecycleOwner){
                Toast.makeText(this.requireContext(), "Catch that product Tiger! I am calling  ${userInfoViewModel.user.value?.phone_number}", Toast.LENGTH_SHORT).show()
                val dialintnt = Intent(Intent.ACTION_DIAL)
                dialintnt.setData(Uri.parse("tel:"+userInfoViewModel.user.value?.phone_number))
                startActivity(dialintnt)
            }

        }
        binding.btnSendMessage.setOnClickListener {
            val currentItem = sharedViewModel.detailsProduct.value
            val manager = (context as AppCompatActivity).supportFragmentManager
            if (currentItem!=null){
                val temp = Order(
                    currentItem.username,
                    "",
                    MarketPlaceApplication.username,
                    arrayListOf(),
                    currentItem.price_per_unit,
                    currentItem.units,
                    currentItem.description,
                    currentItem.title,
                    currentItem.images,
                    currentItem.creation_time,
                    ""
                )
                sharedViewModel.saveOrderToAdd(temp)
                MessageFragment(currentItem.username, currentItem.title, currentItem.price_per_unit+" "+currentItem.price_type+"/"+currentItem.amount_type,currentItem.is_active, currentItem.creation_time, currentItem.product_id, true).show(manager, "CustomManager")

            }


        }
    }


    private fun initialize() {
        val currentProduct = sharedViewModel.detailsProduct.value
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
            binding.productDetailsActive.setTextColor(resources.getColorStateList(R.color.bottom_menu_bar_color))
        }else{
            binding.productDetailsActive.text = "inactive"
            binding.productDetailsActive.setTextColor(resources.getColorStateList(R.color.wrapedWhite))
        }
        binding.productDetailsDescription.text = currentProduct?.description
        val date = Date(currentProduct!!.creation_time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        binding.productDetailsDate.text = format.format(date)
        binding.productDetailsPriceAndUnit.text = currentProduct?.price_per_unit + " " + currentProduct?.price_type+"/"+ currentProduct?.amount_type


    }

    private fun setImageInSlider(images: ArrayList<String>, imageSlider: SliderView) {
        val adapter = MySliderImageAdapter()
        adapter.renewItems(images)
        imageSlider.setSliderAdapter(adapter)
        imageSlider.isAutoCycle = false
        imageSlider.startAutoCycle()
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