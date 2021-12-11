package com.example.market_place.adapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.market_place.R
import com.example.market_place.fragments.market.CustomDialog
import com.example.market_place.model.Order
import com.example.market_place.model.Product
import com.example.market_place.viewmodels.AddOrderViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.viewmodels.SharedViewModel


class DataAdapter(
    private var list: ArrayList<Product>,
    private val context: Context,
    private val listener: OnItemClickListener,
    private val listener2: OnItemLongClickListener,
    private val addOrderViewModel: AddOrderViewModel,
    private val sharedViewModel: SharedViewModel
) :
    RecyclerView.Adapter<DataAdapter.DataViewHolder>() {


    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener{
        fun onItemLongClick(position: Int)
    }

    // 1. user defined ViewHolder type - Embedded class!
    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        val btnOrderNow:Button = itemView.findViewById(R.id.btn_order_now)
        val textView_name: TextView = itemView.findViewById(R.id.item_name)
        val textView_price: TextView = itemView.findViewById(R.id.price_and_currency)
        val textView_seller: TextView = itemView.findViewById(R.id.profile_name)
        val imageView: ImageView = itemView.findViewById(R.id.profile_image)
        val profileImageView:ImageView = itemView.findViewById(R.id.profile_image)

        init{

            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
            btnOrderNow.setOnClickListener {
                Toast.makeText(context,"Orederd ${list.get(adapterPosition).title}",Toast.LENGTH_SHORT).show()
            }
        }
        override fun onClick(p0: View?) {
            val currentPosition = this.adapterPosition
            listener.onItemClick(currentPosition)

        }

        override fun onLongClick(p0: View?): Boolean {
            val currentPosition = this.adapterPosition
            listener2.onItemLongClick(currentPosition)
            return true
        }

    }

    // 2. Called only a few times = number of items on screen + a few more ones
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return DataViewHolder(itemView)
    }


    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = list[position]
        holder.textView_name.text = currentItem.title
        holder.textView_price.text = currentItem.price_per_unit+" "+currentItem.price_type+"/"+currentItem.amount_type
        holder.textView_seller.text = currentItem.username
        val images = currentItem.images
        if( images != null && images.size > 0) {
            Log.d("xxx", "#num_images: ${images.size}")
        }
        Glide.with(this.context)
            .load(R.drawable.profile_icon)
            .override(200, 200)
            .into(holder.imageView);
        Glide.with(this.context)
            .load(R.drawable.ic_bazaar)
            .override(200, 200)
            .into(holder.imageView);
        holder.btnOrderNow.setOnClickListener {
            var status =""
            if (currentItem.is_active){
                status = "OPEN"
            }
            val temp = Order(
                MarketPlaceApplication.username,
                "",
                currentItem.username,
                arrayListOf(),
                currentItem.price_per_unit,
                currentItem.units,
                currentItem.description,
                currentItem.title,
                currentItem.images,
                currentItem.creation_time,
                status
            )
            sharedViewModel.saveOrderToAdd(temp)
            val manager = (context as AppCompatActivity).supportFragmentManager

            CustomDialog().show(manager, "CustomManager")

        }
    }

    override fun getItemCount() = list.size

    // Update the list
    fun setData(newlist: ArrayList<Product>){
        list = newlist
    }
}