package com.example.market_place.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.market_place.R
import com.example.market_place.model.Message

import com.example.market_place.viewmodels.ListMessagesViewModel
import com.example.market_place.viewmodels.SharedViewModel

class MassageAdapter(
    private var list: ArrayList<Message>,
    private val context: Context,
    private val listener: DataAdapter.OnItemClickListener,
    private val listener2: DataAdapter.OnItemLongClickListener,
    private val listMessagesViewModel: ListMessagesViewModel,
    private val sharedViewModel: SharedViewModel
) :
    RecyclerView.Adapter<MassageAdapter.DataViewHolder>()  {
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener{
        fun onItemLongClick(position: Int)
    }

    // 1. user defined ViewHolder type - Embedded class!
    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        val textViewMessage: TextView = itemView.findViewById(R.id.message_product)
        val textView_seller: TextView = itemView.findViewById(R.id.message_sale_profile_name)


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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MassageAdapter.DataViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return DataViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MassageAdapter.DataViewHolder, position: Int) {
        val currentItem = list[position]
        holder.textView_seller.text = currentItem.username
        holder.textViewMessage.text = currentItem.message
    }

    override fun getItemCount() = list.size

    // Update the list
    fun setData(newlist: ArrayList<Message>){
        list = newlist
    }
}