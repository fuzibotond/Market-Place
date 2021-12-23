package com.example.market_place.adapter


import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatImageButton
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.market_place.R
import com.example.market_place.model.Order
import com.example.market_place.viewmodels.AddOrderViewModel
import com.example.market_place.viewmodels.SharedViewModel
import com.example.market_place.viewmodels.UpdateAssetViewModel
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class OrdersAdapter(
    private var list: ArrayList<Order>,
    private val context: Context,
    private val listener: OnItemClickListener,
    private val listener2: OnItemLongClickListener,
    private val sharedViewModel: SharedViewModel,
    private val addOrderViewModel: AddOrderViewModel
) :
    RecyclerView.Adapter<OrdersAdapter.DataViewHolder>() {
    val itemCategoryNameList: ArrayList<String> = arrayListOf("Incoming", "Accepted","Declined", "Delivering", "Delivered")


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
        val btnAccept:MaterialButton  = itemView.findViewById(R.id.btn_sale_accept)
        val btnExtend:AppCompatImageButton = itemView.findViewById(R.id.btn_sale_extend)
        val btnCancel: MaterialButton = itemView.findViewById(R.id.btn_sale_cancel)
        //        textviews
        val textViewName: TextView = itemView.findViewById(R.id.sale_item_name)

        val textViewSeller: TextView = itemView.findViewById(R.id.sale_profile_name)
        val textViewDateTime: TextView = itemView.findViewById(R.id.sale_date_time)
        val textViewItemDescription: TextView = itemView.findViewById(R.id.sale_item_description)
        val textViewAmount: TextView = itemView.findViewById(R.id.sale_amount)
        val textViewPrice: TextView = itemView.findViewById(R.id.sale_price)
        //        imageviews
        val imageViewProfile: ImageView = itemView.findViewById(R.id.sale_profile_image)
        val imageView: ImageView = itemView.findViewById(R.id.sale_profile_image)
        val spinnerIncomingOrder: Spinner = itemView.findViewById(R.id.sale_incoming_orders)
        val textViewItemState: TextView = itemView.findViewById(R.id.sale_item_state)



        init{

            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)

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
        holder.spinnerIncomingOrder.visibility = View.GONE
        holder.textViewItemState.visibility = View.VISIBLE
        holder.textViewItemState.text = currentItem.status
        holder.textViewName.text = currentItem.title
        holder.textViewSeller.text = currentItem.owner_username
        holder.textViewItemDescription.text = currentItem.description
        holder.textViewAmount.text = currentItem.units
        holder.textViewPrice.text = currentItem.price_per_unit
        val date = Date(currentItem.creation_time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        holder.textViewDateTime.text = format.format(date)
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
        holder.spinnerIncomingOrder?.adapter = this.context?.let { ArrayAdapter(it.applicationContext, R.layout.sales_dropdown,itemCategoryNameList ) } as SpinnerAdapter
        holder.spinnerIncomingOrder?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                val type = parent?.getItemAtPosition(position).toString()


            }
        }
        holder.textViewSeller.setOnClickListener {
            sharedViewModel.saveDetailedUser(currentItem.username)
            holder.itemView.findNavController().navigate(R.id.profileDetailsFragment)
        }
        holder.imageViewProfile.setOnClickListener {
            sharedViewModel.saveDetailedUser(currentItem.username)
            holder.itemView.findNavController().navigate(R.id.profileDetailsFragment)
        }
        holder.btnAccept.setOnClickListener {
            holder.btnCancel.visibility = View.GONE
            showDefaultDialog(holder.itemView, currentItem.order_id,holder.spinnerIncomingOrder.selectedItem.toString())
            Toast.makeText(context,"Orederd ${list.get(position).title} has been accepted",Toast.LENGTH_SHORT).show()

        }
        holder.btnCancel.setOnClickListener {
            holder.btnCancel.isClickable = false
            showDefaultDialog(holder.itemView, currentItem.order_id,holder.spinnerIncomingOrder.selectedItem.toString())
            Toast.makeText(context,"Orederd ${list.get(position).title} has been canceled",Toast.LENGTH_SHORT).show()
        }
        holder.btnExtend.setOnClickListener {
            Toast.makeText(context,"Orederd ${list.get(position).description} has been extended",Toast.LENGTH_SHORT).show()
        }
        if (sharedViewModel.orderIsAcceptedIndicitaor.value!=null){
            if (currentItem.status == "OPEN"){
                holder.btnAccept.visibility = View.GONE
            }else{
                holder.btnCancel.visibility = View.GONE
            }
        }
        holder.btnAccept.visibility = View.GONE
        holder.btnAccept.isClickable = false
        holder.btnExtend.setOnClickListener {
            Toast.makeText(context,"Orederd ${list.get(position).description} has been extended",Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = list.size

    // Update the list
    fun setData(newlist: ArrayList<Order>){
        list = newlist
    }
    private fun showDefaultDialog(itemView: View, product_id: String , state:String) {
        val alertDialog = AlertDialog.Builder(this.context)

        alertDialog.apply {
            setIcon(R.drawable.b_icon)
            setTitle("You will modify state to ${state}")
            setMessage("Are you shure about that")
            setPositiveButton("Yes") { _, _ ->
                GlobalScope.launch {
                    addOrderViewModel.removeOrder(product_id)
                }
                itemView.findNavController().navigate(R.id.myFaresFragment)

            }
            setNegativeButton("No") { _, _ ->

            }
            setNeutralButton("Cancel") { _, _ ->

            }
        }.create().show()
    }
}

