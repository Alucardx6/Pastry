package ir.abyx.pastry.mvp.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.abyx.pastry.adapter.recycler.CatsRecyclerAdapter
import ir.abyx.pastry.androidWrapper.PicassoHandler
import ir.abyx.pastry.data.remote.dataModel.ParentCategoryModel
import ir.abyx.pastry.databinding.FragmentCakeCatsBinding
import ir.abyx.pastry.ui.activity.CustomCakeActivity

class ViewCakeCatsFragment(private val context: Context) {

    val binding = FragmentCakeCatsBinding.inflate(LayoutInflater.from(context))

    fun initialize(cats: ParentCategoryModel) {
        binding.apply {
            recyclerCakeCats.apply {
                layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
                adapter = CatsRecyclerAdapter(context, cats.categories)
            }

            if (!cats.banner.isNullOrEmpty())
                PicassoHandler.setPicassoBanner(imgCatsBanner, cats.banner)

            imgCatsBanner.setOnClickListener {
                context.startActivity(Intent(context, CustomCakeActivity::class.java))
            }
        }
    }

    fun endProgress() {
        binding.apply {
            progressBar.visibility = View.GONE
            viewGroup.visibility = View.VISIBLE
        }
    }
}