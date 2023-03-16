package com.example.shoppinglist.data
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository


class ShopListRepositoryImpl(
    application: Application
): ShopListRepository{
    private val shopListDao = AppDataBase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()
    override suspend fun addShopItem(shopItem: ShopItem){
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
   }

     override suspend fun removeShopItem(shopItem: ShopItem) {
     shopListDao.deleteShopItem(shopItem.id)
    }

    override fun getShopList(): LiveData<List<ShopItem>> = MediatorLiveData<List<ShopItem>>().apply{
            addSource(shopListDao.getShopList()){
                value = mapper.mapListDbModelToEntity(it)
            }
    }

     override suspend fun getShopItem(shopItemId: Int): ShopItem {
       val dbModel = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

     override suspend fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

}