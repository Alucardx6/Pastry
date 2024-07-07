package ir.abyx.pastry.adapter.carousel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ir.abyx.pastry.androidWrapper.PicassoHandler
import ir.abyx.pastry.data.remote.dataModel.Related
import ir.abyx.pastry.databinding.ItemCarouselBinding
import ir.abyx.pastry.mvp.ext.OthersUtilities

class CarouselAdapter(private val items: ArrayList<Related>) :
    RecyclerView.Adapter<CarouselAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            ItemCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.setData(items[position])

    override fun getItemCount(): Int = items.size

    inner class CustomViewHolder(private val binding: ItemCarouselBinding) :
        ViewHolder(binding.root) {

        fun setData(item: Related) {
            binding.txtPastryName.text = item.title
            binding.txtMainPrice.text = OthersUtilities.splitPrice(item.price)
            PicassoHandler.setPicasso(binding.imgPastry, item.thumbnail)
        }
    }
}