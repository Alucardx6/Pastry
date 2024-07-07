package ir.abyx.pastry.adapter.recycler

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.abyx.pastry.androidWrapper.PicassoHandler
import ir.abyx.pastry.data.remote.dataModel.PastriesModel
import ir.abyx.pastry.databinding.CustomRecyclerItemBinding
import ir.abyx.pastry.mvp.ext.OthersUtilities
import ir.abyx.pastry.ui.activity.DetailPastryActivity

class PopularPastryRecyclerAdapter(
    private val context: Context,
    private val pastries: ArrayList<PastriesModel>
) : RecyclerView.Adapter<PopularPastryRecyclerAdapter.PopularPastryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = PopularPastryViewHolder(
        CustomRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount() = pastries.size

    override fun onBindViewHolder(holder: PopularPastryViewHolder, position: Int) {
        holder.setData(pastries[position])
    }

    inner class PopularPastryViewHolder(
        private val binding: CustomRecyclerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setData(data: PastriesModel) {

            binding.root.visibility = View.VISIBLE
            binding.txtPastryName.text = data.title
            binding.txtMainPrice.text = OthersUtilities.splitPrice(data.price)

            if (data.has_discount) {

                binding.txtMainPrice.paintFlags =
                    binding.txtMainPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.txtMainPrice.setTextColor(Color.GRAY)

                binding.txtOffPrice.text = OthersUtilities.splitPrice(data.sale_price)
                binding.txtOffPercent.text = data.discount

            } else
                binding.offGroup.visibility = View.GONE

            if (data.thumbnail.isNotEmpty())
                PicassoHandler.setPicasso(binding.imgPastry, data.thumbnail)

            binding.root.setOnClickListener {
                val intent = Intent(context, DetailPastryActivity::class.java)
                intent.putExtra("id", data.ID)
                context.startActivity(intent)
            }

        }

    }

}