package com.example.shoppinglist.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.databinding.FragmentShopItemBinding
import com.example.shoppinglist.domain.ShopItem

class ShopItemFragment: Fragment() {

    private lateinit var viewModel: ShopItemViewModel
    private  lateinit var onEditingFinishedListener: OnEditingFinishedListener


    private var modeScreen: String = MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNDEFINED_ID
    private var _binding: FragmentShopItemBinding? = null
    private val binding: FragmentShopItemBinding
    get() = _binding ?: throw RuntimeException("FragmentShopItemBinding == null")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnEditingFinishedListener){
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity most implement listener")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentShopItemBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        binding.shopItemViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        addTextChangeList()
        launchRightMode()
        observeViewModel()
    }









 private fun observeViewModel() {
     viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
         onEditingFinishedListener?.onEditingFinished()
     }
 }

    interface OnEditingFinishedListener{
        fun onEditingFinished()
    }

 private fun launchRightMode() {
     when (modeScreen) {
         MODE_EDIT -> launchEditMode()
         MODE_ADD -> launchAddMode()
     }
 }

 private fun launchEditMode(){
   viewModel.getShopItem(shopItemId)


     binding.saveButton.setOnClickListener {
     viewModel.editShopItemUseCase(binding.etName.text?.toString(),binding.etCount.text?.toString())
     }



 }
 private fun launchAddMode(){
     binding.saveButton.setOnClickListener {
         viewModel.addShopItem(binding.etName.text?.toString(),binding.etCount.text?.toString())
     }
 }



    companion object {
        private const val MODE_UNKNOWN = ""
        private const val MODE_ID = "EXTRA_MODE_ID"
        private const val MODE = "extra_mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"


        fun newInstanceAddItem() : ShopItemFragment{
            return ShopItemFragment().apply{
                arguments = Bundle().apply {
                    putString(MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopId: Int) : ShopItemFragment{
            return ShopItemFragment().apply{
                arguments = Bundle().apply {
                    putString(MODE, MODE_EDIT)
                    putInt(MODE_ID,shopId)
                }
            }
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
         modeScreen = mode
        if (modeScreen == MODE_EDIT) {
            if (!args.containsKey(MODE_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = args.getInt(MODE_ID, ShopItem.UNDEFINED_ID)
        }
    }



    private fun addTextChangeList(){

        binding.etName.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        binding.etCount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }
}