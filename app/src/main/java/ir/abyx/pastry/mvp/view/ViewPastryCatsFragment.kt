package ir.abyx.pastry.mvp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.abyx.pastry.adapter.recycler.CatsRecyclerAdapter
import ir.abyx.pastry.androidWrapper.PicassoHandler
import ir.abyx.pastry.data.remote.dataModel.ParentCategoryModel
import ir.abyx.pastry.databinding.FragmentPastryCatsBinding

class ViewPastryCatsFragment(private val context: Context) {

    val binding = FragmentPastryCatsBinding.inflate(LayoutInflater.from(context))

    fun initialize(cats: ParentCategoryModel) {
        binding.apply {
            recyclerCats.apply {
                layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
                adapter = CatsRecyclerAdapter(context, cats.categories)
            }

            if (!cats.banner.isNullOrEmpty())
                PicassoHandler.setPicassoBanner(imgCatsBanner, cats.banner)
        }
    }

    fun endProgress() {
        binding.cardView.visibility = View.VISIBLE
        binding.recyclerCats.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

}