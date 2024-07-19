package ir.abyx.pastry.mvp.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.abyx.pastry.R
import ir.abyx.pastry.adapter.recycler.CartRecyclerAdapter
import ir.abyx.pastry.adapter.recycler.ShopAddressRecyclerAdapter
import ir.abyx.pastry.data.remote.dataModel.Address
import ir.abyx.pastry.data.remote.dataModel.CartModel
import ir.abyx.pastry.databinding.FragmentShoppingCartBinding
import ir.abyx.pastry.mvp.ext.OthersUtilities
import ir.abyx.pastry.mvp.ext.ShopState
import ir.abyx.pastry.mvp.ext.ViewUtils
import ir.abyx.pastry.ui.activity.EditAddressActivity

class ViewShoppingCartFragment(
    private val context: Context
) {
    private lateinit var addressAdapter: ShopAddressRecyclerAdapter
    private lateinit var myAdapter: CartRecyclerAdapter
    private lateinit var shopState: ShopState
    val binding = FragmentShoppingCartBinding.inflate(LayoutInflater.from(context))

    @SuppressLint("SetTextI18n")
    fun initialize(cart: CartModel, viewUtils: ViewUtils) {
        shopState = ShopState.SHOP_CART
        myAdapter = CartRecyclerAdapter(cart.items, viewUtils)
        binding.apply {
            if (cart.items.isNullOrEmpty()) {
                viewGroup.visibility = View.INVISIBLE
                emptyCart.visibility = View.VISIBLE
            } else
                viewGroup.visibility = View.VISIBLE

            recyclerViewProduct.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = myAdapter
            }

            txtSend.text = OthersUtilities.splitPrice(100000)
            txtFinalAmount.text = OthersUtilities.splitPrice(cart.total)
            if (cart.quantity == cart.items.size)
                txtDiscount.text = "0"
            else
                txtDiscount.text = OthersUtilities.splitPrice(cart.total - cart.subtotal)

            btnAddAddress.setOnClickListener {
                context.startActivity(Intent(context, EditAddressActivity::class.java))
            }
        }

        binding.btnNext.getView().setOnClickListener {
            if (shopState == ShopState.SHOP_CART) {
                shopState = ShopState.CHOOSE_ADDRESS
                address()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun rbPost(address: Address) {
        addressAdapter =
            ShopAddressRecyclerAdapter(address.addresses)
    }

    @SuppressLint("SetTextI18n")
    fun address() {
        binding.apply {
            constraintOrderType.visibility = View.VISIBLE
            txtAddressText.visibility = View.VISIBLE
            btnAddAddress.visibility = View.VISIBLE
            postAmount.visibility = View.VISIBLE
            shopState.setImageResource(R.drawable.shopping_state_2)

            recyclerViewProduct.background = null
            recyclerViewProduct.adapter = addressAdapter

            rbDeliver.setOnClickListener {
                rbDeliver.isChecked = true
                rbInPerson.isChecked = false
                viewDeliver.setBackgroundResource(R.drawable.back_radio_sell_white)
                viewInPerson.setBackgroundResource(R.drawable.back_radio_sell_transparent)
                txtAddressText.text = "آدرس محل تحویل سفارش خود را انتخاب کنید."
                btnAddAddress.visibility = View.VISIBLE
                recyclerViewProduct.visibility = View.VISIBLE
                recyclerViewProduct.adapter = addressAdapter

            }

            rbInPerson.setOnClickListener {
                rbDeliver.isChecked = false
                rbInPerson.isChecked = true
                viewDeliver.setBackgroundResource(R.drawable.back_radio_sell_transparent)
                viewInPerson.setBackgroundResource(R.drawable.back_radio_sell_white)
                txtAddressText.text =
                    "برای تحویل سفارش از ساعت 8الی 12 و 17 الی 22 می توانید در روز 1401/12/11 به شیرینی فرانسوی واقع در خیابان مدرس ..... مراجعه کنید."
                btnAddAddress.visibility = View.GONE
                recyclerViewProduct.visibility = View.GONE
            }

            btnNext.getView().text = "پرداخت نهایی"
        }
    }

    fun updateRecyclerAddress(data: Address) {
        addressAdapter.dataUpdate(data.addresses)
    }

    fun updateRecycler(data: CartModel) {
        myAdapter.dataUpdate(data.items)
    }

    fun endProgress() {
        binding.progressBar.visibility = View.GONE
    }

}