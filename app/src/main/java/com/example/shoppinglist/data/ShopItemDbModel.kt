package com.example.shoppinglist.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "shop_items")
data class ShopItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val count: Int,
    val name: String,
    val enabled: Boolean,
)




