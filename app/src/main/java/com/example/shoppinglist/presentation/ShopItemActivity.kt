package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

 /* private lateinit var viewModel: ShopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button



*/
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


       /*
        viewModel =ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews()
        addTextChangeList()

        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.errorInputCount.observe(this) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            tilCount.error = message
        }

        viewModel.errorInputName.observe(this) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilName.error = message
        }

        viewModel.shouldCloseScreen.observe(this) {
            finish()
        }
    }



    private fun launchEditMode(){
      viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(this){
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }

        buttonSave.setOnClickListener {
        viewModel.editShopItemUseCase(etName.text?.toString(),etCount.text?.toString())
        }



    }
    private fun launchAddMode(){
        buttonSave.setOnClickListener {
            viewModel.addShopItem(etName.text?.toString(),etCount.text?.toString())
        }
    }

    private fun initViews(){
        tilName = findViewById(R.id.til_name)
        tilCount = findViewById(R.id.til_count)
        etName = findViewById(R.id.et_name)
        etCount = findViewById(R.id.et_count)
        buttonSave = findViewById(R.id.save_button)
    }*/

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
    /*
    private fun addTextChangeList(){

        etName.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        etCount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }*/
}