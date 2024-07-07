package ir.abyx.pastry.mvp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.abyx.pastry.adapter.recycler.ProductListRecyclerAdapter
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.data.remote.dataModel.PastryModel
import ir.abyx.pastry.databinding.ActivityFavoriteBinding

class ViewFavoriteActivity(
    context: Context,
    private val activityUtils: ActivityUtils
) {

    val binding = ActivityFavoriteBinding.inflate(LayoutInflater.from(context))

    fun initialize(data: ArrayList<PastryModel>) {

        binding.recyclerFavorite.getRecycler().apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = ProductListRecyclerAdapter(context, data)
        }

        if (data.isNotEmpty())
            binding.txtNotFavorite.visibility = View.INVISIBLE
    }

    fun endProgress() {
        binding.progressBar.visibility = View.GONE
    }

    fun backButton() {
        binding.customAppBar.getBackIcon().setOnClickListener {
            activityUtils.finished()
        }
    }

}