package ir.abyx.pastry.adapter.recycler

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ir.abyx.pastry.androidWrapper.PicassoHandler
import ir.abyx.pastry.data.remote.dataModel.PastriesModel
import ir.abyx.pastry.databinding.CustomRecyclerItemBinding
import ir.abyx.pastry.mvp.ext.OthersUtilities
import ir.abyx.pastry.ui.activity.DetailPastryActivity

class SpecialOfferRecyclerAdapter(
    private val context: Context,
    private val pastries: ArrayList<PastriesModel>
) : RecyclerView.Adapter<SpecialOfferRecyclerAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder = CustomViewHolder(
        CustomRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.setData(pastries[position], position)

    override fun getItemCount(): Int = pastries.size


    inner class CustomViewHolder(private val binding: CustomRecyclerItemBinding) :
        ViewHolder(binding.root) {
        fun setData(pastry: PastriesModel, position: Int) {

            if (position == 0) {
                binding.root.visibility = View.GONE
            } else {
                binding.root.visibility = View.VISIBLE
                binding.txtPastryName.text = pastry.title
                binding.txtMainPrice.text = OthersUtilities.splitPrice(pastry.price)
                Log.i("Discount", pastry.has_discount.toString())
                if (pastry.has_discount) {
                    binding.txtMainPrice.paintFlags =
                        binding.txtMainPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    binding.txtMainPrice.setTextColor(Color.GRAY)

                    binding.txtOffPrice.text =
                        OthersUtilities.splitPrice(pastry.sale_price)
                    binding.txtOffPercent.text = pastry.discount
                } else
                    binding.offGroup.visibility = View.GONE


                if (pastry.thumbnail.isNotEmpty())
                    PicassoHandler.setPicasso(binding.imgPastry, pastry.thumbnail)

                if (position == pastries.lastIndex) {
                    binding.allView.visibility = View.INVISIBLE
                    binding.more.visibility = View.VISIBLE

                    binding.root.setOnClickListener {

                    }
                }
            }

            binding.root.setOnClickListener {
                val intent = Intent(context, DetailPastryActivity::class.java)
                intent.putExtra("id", pastry.ID)
                context.startActivity(intent)
            }

        }
    }

}