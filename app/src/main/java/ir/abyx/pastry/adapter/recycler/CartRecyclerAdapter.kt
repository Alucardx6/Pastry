package ir.abyx.pastry.adapter.recycler

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ir.abyx.pastry.adapter.recycler.diffUtil.RecyclerDiffUtil
import ir.abyx.pastry.androidWrapper.PicassoHandler
import ir.abyx.pastry.data.remote.dataModel.Addresses
import ir.abyx.pastry.data.remote.dataModel.ItemModel
import ir.abyx.pastry.databinding.RecyclerShoppingCartItemBinding
import ir.abyx.pastry.mvp.ext.OthersUtilities
import ir.abyx.pastry.mvp.ext.ViewUtils

class CartRecyclerAdapter(
    private val items: ArrayList<ItemModel>,
    private val viewUtils: ViewUtils
) :
    RecyclerView.Adapter<CartRecyclerAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            RecyclerShoppingCartItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.setData(items[position], position)

    inner class CustomViewHolder(private val binding: RecyclerShoppingCartItemBinding) :
        ViewHolder(binding.root) {
        fun setData(item: ItemModel, position: Int) {

            binding.apply {

                if (items.lastIndex == position)
                    imgLine.visibility = View.GONE

                if (!item.thumbnail.isNullOrEmpty())
                    PicassoHandler.setPicasso(imgItem, item.thumbnail)
                txtItemName.text = item.title
                txtCount.text = item.quantity.toString()

                var count = txtCount.text.toString().toInt()

                viewPlus.setOnClickListener {
                    if (count < 10)
                        count++
                    if (count > 10)
                        count + 5
                    txtCount.text = count.toString()
                }

                viewMin.setOnClickListener {
                    if (count < 10)
                        count--
                    if (count > 10)
                        count - 5
                    txtCount.text = count.toString()
                }

                imgDelete.setOnClickListener {
                    viewUtils.deleteItem(item.ID)
                }

                if (item.sale_bulk_price != 0 && item.quantity > 1) {
                    txtMainPrice.apply {
                        text = OthersUtilities.splitPrice(item.single_sale_price)
                        paintFlags =
                            paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        setTextColor(Color.GRAY)
                    }
                    txtOffPrice.text = OthersUtilities.splitPrice(item.sale_bulk_price)
                } else {
                    txtMainPrice.text = OthersUtilities.splitPrice(item.single_price)
                    txtOffPrice.visibility = View.GONE
                }
            }
        }
    }

    fun dataUpdate(newList: ArrayList<ItemModel>) {

        val diffCallback = RecyclerDiffUtil(items, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items.clear()
        items.addAll(newList)

        diffResult.dispatchUpdatesTo(this)

    }

}