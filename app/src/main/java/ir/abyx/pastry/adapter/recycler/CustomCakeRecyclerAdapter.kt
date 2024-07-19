package ir.abyx.pastry.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ir.abyx.pastry.androidWrapper.PicassoHandler
import ir.abyx.pastry.data.remote.dataModel.CustomCake
import ir.abyx.pastry.databinding.RecyclerItemListCustomCakeBinding

class CustomCakeRecyclerAdapter(private val customCakes: ArrayList<CustomCake>) :
    RecyclerView.Adapter<CustomCakeRecyclerAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            RecyclerItemListCustomCakeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = customCakes.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.setData(customCakes[position])

    inner class CustomViewHolder(private val binding: RecyclerItemListCustomCakeBinding) :
        ViewHolder(binding.root) {
        fun setData(customCake: CustomCake) {
            binding.apply {
                if (!customCake.files.isNullOrEmpty())
                    PicassoHandler.setPicasso(imgCake, customCake.files)
                txtOrderId.text = customCake.ID.toString()

                btnMoreDetail.setOnClickListener {

                }
            }
        }
    }
}