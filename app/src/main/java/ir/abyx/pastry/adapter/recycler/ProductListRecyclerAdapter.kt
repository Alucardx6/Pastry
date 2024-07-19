package ir.abyx.pastry.adapter.recycler

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ir.abyx.pastry.adapter.recycler.diffUtil.RecyclerDiffUtil
import ir.abyx.pastry.androidWrapper.PicassoHandler
import ir.abyx.pastry.data.remote.dataModel.PastryModel
import ir.abyx.pastry.databinding.RecyclerItemListProductsBinding
import ir.abyx.pastry.mvp.ext.OthersUtilities
import ir.abyx.pastry.ui.activity.DetailPastryActivity

class ProductListRecyclerAdapter(
    private val context: Context,
    private val items: ArrayList<PastryModel>
) :
    RecyclerView.Adapter<ProductListRecyclerAdapter.CustomViewHolder>(), Filterable {

    private val dataFull = ArrayList<PastryModel>()
    private val dataMain = ArrayList<PastryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            RecyclerItemListProductsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.setData(items[position])

    inner class CustomViewHolder(private val binding: RecyclerItemListProductsBinding) :
        ViewHolder(binding.root) {

        fun setData(item: PastryModel) {
            binding.txtPastryName.text = item.title
            binding.txtPriceMain.text = OthersUtilities.splitPrice(item.price)

            if (item.thumbnail.isNotEmpty())
                PicassoHandler.setPicasso(binding.imgProduct, item.thumbnail)

            if (item.has_discount) {
                binding.txtPriceMain.apply {
                    paintFlags =
                        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    setTextColor(Color.GRAY)
                }

                binding.txtPriceOff.apply {
                    visibility = View.VISIBLE
                    text = OthersUtilities.splitPrice(item.sale_price)
                }

                binding.txtOff.text = item.discount
            } else {
                binding.offGroup.visibility = View.GONE
            }

            binding.root.setOnClickListener {
                val intent = Intent(context, DetailPastryActivity::class.java)
                intent.putExtra("id", item.ID)
                context.startActivity(intent)
            }
        }
    }

    override fun getFilter(): Filter =
        object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val data = ArrayList<PastryModel>()

                if (constraint.isNullOrEmpty())
                    data.addAll(dataFull)
                else {
                    val filter = constraint.toString().trim()
                    for (item in dataFull) {
                        if (item.title.contains(filter))
                            data.add(item)
                    }
                }

                items.clear()
                items.addAll(data)

                return FilterResults()

            }

            override fun publishResults(p0: CharSequence?, result: FilterResults?) {
                dataUpdate(items)
            }

        }

    private fun dataUpdate(newList: ArrayList<PastryModel>) {

        val diffCallback = RecyclerDiffUtil(dataMain, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        dataMain.clear()
        dataMain.addAll(newList)

        diffResult.dispatchUpdatesTo(this)

    }
}