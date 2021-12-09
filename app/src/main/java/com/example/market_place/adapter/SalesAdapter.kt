package com.example.market_place.adapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.market_place.R
import com.example.market_place.model.Order
import com.example.market_place.model.Product


class SalesAdapter(
    private var list: ArrayList<Order>,
    private val context: Context,
    private val listener: OnItemClickListener,
    private val listener2: OnItemLongClickListener
) :
    RecyclerView.Adapter<SalesAdapter.DataViewHolder>() {

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener{
        fun onItemLongClick(position: Int)
    }

    // 1. user defined ViewHolder type - Embedded class!
    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
//        buttons
        val btnAccept:AppCompatImageButton  = itemView.findViewById(R.id.btn_sale_accept)
        val btnExtend:AppCompatImageButton = itemView.findViewById(R.id.btn_sale_extend)
        val btnCancel:AppCompatImageButton  = itemView.findViewById(R.id.btn_sale_cancel)
//        textviews
        val textViewName: TextView = itemView.findViewById(R.id.sale_item_name)
        val textViewPriceandCurrency: TextView = itemView.findViewById(R.id.sale_price_and_currency)
        val textViewSeller: TextView = itemView.findViewById(R.id.sale_profile_name)
        val textViewDateTime: TextView = itemView.findViewById(R.id.sale_date_time)
        val textViewItemDescription: TextView = itemView.findViewById(R.id.sale_item_description)
        val textViewAmount: TextView = itemView.findViewById(R.id.sale_amount)
        val textViewPrice: TextView = itemView.findViewById(R.id.sale_price)
//        imageviews
        val imageViewProfile: ImageView = itemView.findViewById(R.id.sale_profile_image)
        val imageView: ImageView = itemView.findViewById(R.id.sale_profile_image)
        val spinnerIncomingOrder: Spinner = itemView.findViewById(R.id.sale_incoming_orders)



        init{

            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
            btnAccept.setOnClickListener {
                Toast.makeText(context,"Orederd ${list.get(adapterPosition).title} has been accepted",Toast.LENGTH_SHORT).show()
            }
            btnCancel.setOnClickListener {
                Toast.makeText(context,"Orederd ${list.get(adapterPosition).title} has been canceled",Toast.LENGTH_SHORT).show()
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
            LayoutInflater.from(parent.context).inflate(R.layout.my_fares_item_layout, parent, false)
        return DataViewHolder(itemView)
    }


    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = list[position]
        holder.textViewName.text = currentItem.title
        holder.textViewPrice.text = currentItem.price_per_unit
        holder.textViewSeller.text = currentItem.username
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
    }

    override fun getItemCount() = list.size

    // Update the list
    fun setData(newlist: ArrayList<Order>){
        list = newlist
    }
}