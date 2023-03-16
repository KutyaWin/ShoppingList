package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    suspend fun removeShopItem(shopItem:ShopItem)

     fun getShopList(): LiveData<List<ShopItem>>

    suspend fun getShopItem(shopItemId: Int): ShopItem

    suspend fun editShopItem(shopItem: ShopItem)

    suspend fun addShopItem(shopItem: ShopItem)
}