package com.example.market_place.adapter



import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.market_place.R
import com.example.market_place.fragments.market.MyMarketFragment
import com.example.market_place.model.Product
import com.example.market_place.viewmodels.AddProductViewModel
import com.example.market_place.viewmodels.SharedViewModel
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DataAdapterForMarketSale(
    private var list: ArrayList<Product>,
    private val context: Context,
    private val listener: OnItemClickListener,
    private val listener2: MyMarketFragment,
    private val addProductViewModel: AddProductViewModel,
    private val sharedViewModel: SharedViewModel

) :
    RecyclerView.Adapter<DataAdapterForMarketSale.DataViewHolder>() {

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener{
        fun onItemLongClick(position: Int)
    }

    // 1. user defined ViewHolder type - Embedded class!
    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        val textView_name: TextView = itemView.findViewById(R.id.my_market_item_name)
        val textView_is_active: TextView = itemView.findViewById(R.id.my_market_is_active)
        val textView_price: TextView = itemView.findViewById(R.id.my_market_price_and_currency)
        val textView_seller: TextView = itemView.findViewById(R.id.my_market_profile_name)
        val imageView: ImageView = itemView.findViewById(R.id.my_market_profile_image)
        val profileImageView:ImageView = itemView.findViewById(R.id.my_market_profile_image)
        val btnRemoveProduct: MaterialButton = itemView.findViewById(R.id.btn_remove_product)

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
            LayoutInflater.from(parent.context).inflate(R.layout.my_market_item_layout, parent, false)
        return DataViewHolder(itemView)
    }


    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = list[position]
        holder.textView_name.text = currentItem.title
        holder.textView_price.text = currentItem.price_per_unit+" "+currentItem.price_type+"/"+currentItem.amount_type
        holder.textView_seller.text = currentItem.username
        if (currentItem.is_active){
            holder.textView_is_active.text = "active"
            holder.textView_is_active.setTextColor(Color.parseColor("#FF03DAC5"))
        }

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
        holder.btnRemoveProduct.setOnClickListener{
            AlertDialog.Builder(context as AppCompatActivity)
                .setTitle("Back?")
                .setMessage("Are you sure about that?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes"){ _,_ ->
                    GlobalScope.launch {
                        addProductViewModel.removeProduct(currentItem.product_id)
                    }
                }
                .show()

        }
        holder.textView_seller.setOnClickListener {
            sharedViewModel.saveDetailedUser(currentItem.username)
            holder.itemView.findNavController().navigate(R.id.profileDetailsFragment)
        }
        holder.profileImageView.setOnClickListener {
            sharedViewModel.saveDetailedUser(currentItem.username)
            holder.itemView.findNavController().navigate(R.id.profileDetailsFragment)
        }
    }

    override fun getItemCount() = list.size

    // Update the list
    fun setData(newlist: ArrayList<Product>){
        list = newlist
    }
}