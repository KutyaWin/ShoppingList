package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import kotlin.RuntimeException
import kotlin.random.Random

object ShopListRepositoryImpl: ShopListRepository {
    private val set = sortedSetOf<ShopItem>({a,b->a.id.compareTo(b.id)})
    private val listLD = MutableLiveData<List<ShopItem>>()
    private var autoIncrementId =0
init{
    for(i in 0 until 1000){
        val item =ShopItem(i,"Shop$i", true)
        addShopItem(item)
    }
}
    override fun removeShopItem(shopItem: ShopItem) {
      set.remove(shopItem)
        updateList()
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return listLD
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return set.find {it.id==shopItemId}?:throw RuntimeException("Element with id $shopItemId not found!")
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        removeShopItem(oldElement)
        addShopItem(shopItem)
    }

    override fun addShopItem(shopItem: ShopItem) {
        if(shopItem.id == ShopItem.UNDEFINED_ID){
        shopItem.id  = autoIncrementId++
            }
        set.add(shopItem)
        updateList()
    }
      fun updateList(){
          listLD.value = set.toList()
      }
}