package ir.abyx.pastry.adapter.viewPager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ir.abyx.pastry.androidWrapper.PicassoHandler
import ir.abyx.pastry.databinding.FragmentMainImageSliderBinding

class SliderAdapter(private val items: ArrayList<String>) :
    RecyclerView.Adapter<SliderAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            FragmentMainImageSliderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) = holder.setData(items[position])

    inner class CustomViewHolder(private val binding: FragmentMainImageSliderBinding) :
        ViewHolder(binding.root) {
        fun setData(item: String) {
            PicassoHandler.setPicasso(binding.imgSlider, item)
        }
    }
}