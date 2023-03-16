package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {


     private var modeScreen = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        if(savedInstanceState==null){
            launchRightMode()
        }

    }

    override fun onEditingFinished() {
        finish()
    }
    private fun launchRightMode() {
        val fragment= when(modeScreen){
            MODE_EDIT -> ShopItemFragment.newInstanceEditItem(shopItemId)
            MODE_ADD -> ShopItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown mode $modeScreen")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container,fragment )
            .commit()
    }




    companion object {
        private const val MODE_UNKNOWN = ""
        private const val EXTRA_MODE_ID = "EXTRA_MODE_ID"
        private const val EXTRA_MODE = "extra_mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"


        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_MODE_ID,  shopItemId)
            return intent
        }

    }

    private fun parseIntent(){
        if(!intent.hasExtra(EXTRA_MODE)){
            throw RuntimeException("Param mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_MODE)
        if(mode!= MODE_ADD && mode!= MODE_EDIT){
            throw RuntimeException("Unknown mode $mode")
        }
        modeScreen = mode
        if( modeScreen == MODE_EDIT){
            if(!intent.hasExtra(EXTRA_MODE_ID)) {
                throw RuntimeException("Param mode id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_MODE_ID,shopItemId)
        }

    }

}