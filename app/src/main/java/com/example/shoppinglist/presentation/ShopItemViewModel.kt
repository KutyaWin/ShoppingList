package com.example.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.*

class ShopItemViewModel: ViewModel() {
      private val repository = ShopListRepositoryImpl
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
    get()= _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
    get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
    get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get()= _shouldCloseScreen

    fun getShopItem(shopId: Int){
        val item = getShopItemUseCase.getShopItem(shopId)
        _shopItem.value = item
    }
    fun addShopItem(inputName: String?, inputCount: String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val validate = validateInput(name,count)
        if(validate){
            val shopItem = ShopItem(count,name,true)
            addShopItemUseCase.addShopItem(shopItem)
            finishWork()
        }

    }
    fun editShopItemUseCase(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val validate = validateInput(name,count)
        if(validate){
            _shopItem.value?.let {
                val item = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(item)
                finishWork()
            }

        }

    }
    fun parseName(inputName: String?): String{
        return inputName?.trim() ?: ""
    }
    fun parseCount(inputCount: String?): Int{
       return try {
             inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception){
             0
        }
    }
    fun validateInput(name: String, count: Int): Boolean{
        var result = true
        if(count<=0){
            _errorInputCount.value = true
            result = false
        }

        if(name.isBlank()){
            _errorInputName.value = true
            result = false
        }
          return result
    }
    public fun resetErrorInputName(){
        _errorInputName.value = false
    }
    public fun resetErrorInputCount(){
        _errorInputCount.value = false
    }
    public fun finishWork(){
        _shouldCloseScreen.value = Unit
    }
}