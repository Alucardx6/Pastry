package ir.abyx.pastry.mvp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.databinding.ActivityDiscountBinding

class ViewDiscountActivity(context: Context, private val activityUtils: ActivityUtils) {
    val binding = ActivityDiscountBinding.inflate(LayoutInflater.from(context))

    fun initialize() {
        binding.progressBar.visibility = View.GONE
        // NO API FOUND FOR DISCOUNTS
    }

    fun backButton() {
        binding.customAppBar.getBackIcon().setOnClickListener {
            activityUtils.finished()
        }
    }
}