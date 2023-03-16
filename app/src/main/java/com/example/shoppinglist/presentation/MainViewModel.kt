package com.example.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopListUseCase
import com.example.shoppinglist.domain.RemoveShopItemUseCase
import com.example.shoppinglist.domain.ShopItem
import kotlinx.coroutines.launch


class MainViewModel(application: Application): AndroidViewModel(application) {

    private var repository = ShopListRepositoryImpl(application)

   private  val getShopListUseCase =  GetShopListUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val scope =  viewModelScope

    val shopList = getShopListUseCase.getShopList()


     fun removeShopItem(shopItem: ShopItem){
        scope.launch {
            removeShopItemUseCase.removeShopItem(shopItem)
        }

    }
     fun editShopItem(shopItem: ShopItem){
         scope.launch {
            val newItem = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(newItem)
        }
    }


}