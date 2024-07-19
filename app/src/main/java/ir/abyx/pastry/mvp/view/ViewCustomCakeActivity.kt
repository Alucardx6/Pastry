package ir.abyx.pastry.mvp.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.abyx.pastry.adapter.recycler.CustomCakeRecyclerAdapter
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.data.remote.dataModel.AllCustomCake
import ir.abyx.pastry.databinding.ActivityCustomCakeBinding
import ir.abyx.pastry.ui.activity.RequestCustomCakeActivity

class ViewCustomCakeActivity(
    private val context: Context,
    private val activityUtils: ActivityUtils
) {

    val binding = ActivityCustomCakeBinding.inflate(LayoutInflater.from(context))

    fun initialize(customCakes: AllCustomCake) {

        binding.apply {
            if (customCakes.custom_cakes.isNotEmpty())
                txtNoCustomCake.visibility = View.GONE

            recyclerCustomCake.apply {
                layoutManager =
                    LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = CustomCakeRecyclerAdapter(customCakes.custom_cakes)
            }

            btnAddCustomCake.setOnClickListener {
                context.startActivity(Intent(context, RequestCustomCakeActivity::class.java))
            }
            btnMyOrders.setOnClickListener {
            }
        }
    }

    fun endProgress() {
        binding.apply {
            progressBar.visibility = View.GONE
            viewGroup.visibility = View.VISIBLE
        }
    }

    fun backButton() {
        binding.customAppBar.getBackIcon().setOnClickListener {
            activityUtils.finished()
        }
    }
}