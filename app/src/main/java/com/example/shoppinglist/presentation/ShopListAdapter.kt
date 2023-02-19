package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemHolder>() {
    var shopList = listOf<ShopItem>()
    set(value){
        field=value
        notifyDataSetChanged()
    }

    class ShopItemHolder(view: View): ViewHolder(view){
       val shopItemName = view.findViewById<TextView>(R.id.tv_name)
        val shopItemCount = view.findViewById<TextView>(R.id.tv_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_shop_enabled,
            parent,
            false
        )
        return ShopItemHolder(view)
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun onBindViewHolder(holder: ShopItemHolder, position: Int) {
        val shopItem = shopList[position]
        val status = if(shopItem.enabled){
            "Active"
        }else {
            "Not active"
        }

        holder.itemView.setOnLongClickListener {
         true
        }
        if(shopItem.enabled) {
            holder.shopItemCount.text = shopItem.count.toString()
            holder.shopItemName.text = "${shopItem.name} : $status"
            holder.shopItemName.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.black)
            )
        }

    }

    override fun onViewRecycled(holder: ShopItemHolder) {
        holder.shopItemCount.text =""
        holder.shopItemName.text=""
        holder.shopItemName.setTextColor(
            ContextCompat.getColor(holder.itemView.context,R.color.white)
        )
        super.onViewRecycled(holder)
    }

}