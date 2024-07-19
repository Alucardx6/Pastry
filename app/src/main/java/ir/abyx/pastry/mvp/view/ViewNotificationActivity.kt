package ir.abyx.pastry.mvp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.databinding.ActivityNotificationBinding

class ViewNotificationActivity(context: Context, private val activityUtils: ActivityUtils) {

    val binding = ActivityNotificationBinding.inflate(LayoutInflater.from(context))

    fun initialize() {
        binding.customAppBar.hideAlert()
    }

    fun endProgress() {
        binding.progressBar.visibility = View.GONE
        binding.recyclerNotification.visibility = View.VISIBLE
    }

    fun backButton() {
        binding.customAppBar.getBackIcon().setOnClickListener {
            activityUtils.finished()
        }
    }

}