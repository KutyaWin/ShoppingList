package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter: ListAdapter<ShopItem, ShopListAdapter.ShopItemHolder>(ShopItemDiffCallback()){
    var onShopItemLongClickListener:((ShopItem)->Unit)?=null
    var onShopItemClickListener:((ShopItem)->Unit)?=null


    class ShopItemHolder(view: View): ViewHolder(view){
       val shopItemName = view.findViewById<TextView>(R.id.tv_name)
        val shopItemCount = view.findViewById<TextView>(R.id.tv_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemHolder {
        val layout = when(viewType){
          VIEW_TYPE_ENABLED->R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED->R.layout.item_shop_disabled
            else->throw java.lang.RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
        )
        return ShopItemHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemHolder, position: Int) {
        val shopItem = getItem(position)
        holder.shopItemCount.text = shopItem.count.toString()
        holder.shopItemName.text = shopItem.name
       holder.itemView.setOnLongClickListener {
       onShopItemLongClickListener?.invoke(shopItem)
           true
       }
        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
            true
        }

    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        val status =if(item.enabled){
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
        return status
    }

    companion object{
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101
        const val POOL_MAX_SIZE =15
    }
}