package ir.abyx.pastry.adapter.recycler

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ir.abyx.pastry.androidWrapper.PicassoHandler
import ir.abyx.pastry.data.remote.dataModel.CategoriesModel
import ir.abyx.pastry.databinding.RecyclerItemMainCategoriesBinding
import ir.abyx.pastry.ui.activity.ListPastryActivity

class CatsRecyclerAdapter(
    private val context: Context,
    private val cats: ArrayList<CategoriesModel>
) : RecyclerView.Adapter<CatsRecyclerAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            RecyclerItemMainCategoriesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = cats.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.setData(cats[position])

    inner class CustomViewHolder(private val binding: RecyclerItemMainCategoriesBinding) :
        ViewHolder(binding.root) {

        fun setData(cat: CategoriesModel) {
            binding.apply {
                txtCategory.text = cat.title

                if (!cat.thumbnail.isNullOrEmpty())
                    PicassoHandler.setPicassoCats(imgCategory, cat.thumbnail)
                root.setOnClickListener {
                    val intent = Intent(context, ListPastryActivity::class.java)
                    intent.putExtra("id", cat.ID)
                    context.startActivity(intent)
                }
            }
        }

    }

}