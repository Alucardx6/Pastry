package ir.abyx.pastry.mvp.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import ir.abyx.pastry.data.remote.dataModel.UserData
import ir.abyx.pastry.databinding.ProfileFragmentBinding
import ir.abyx.pastry.ui.activity.AddressActivity
import ir.abyx.pastry.ui.activity.CustomCakeActivity
import ir.abyx.pastry.ui.activity.DiscountActivity
import ir.abyx.pastry.ui.activity.FavoriteActivity
import ir.abyx.pastry.ui.activity.NotificationActivity
import ir.abyx.pastry.ui.activity.UserActivity

class ViewProfileFragment(private val context: Context) {

    val binding = ProfileFragmentBinding.inflate(LayoutInflater.from(context))

    fun initialize(data: UserData) {
        binding.txtUsername.text = data.fullname
        binding.txtPhone.text = data.phone
    }

    fun onClick() {

        binding.apply {
            imgUserInfo.setOnClickListener {
                context.startActivity(Intent(context, UserActivity::class.java))
            }

            imgFavorite.setOnClickListener {
                context.startActivity(Intent(context, FavoriteActivity::class.java))
            }

            imgAddress.setOnClickListener {
                context.startActivity(Intent(context, AddressActivity::class.java))
            }

            imgMyOff.setOnClickListener {
                context.startActivity(Intent(context, DiscountActivity::class.java))
            }

            imgCake.setOnClickListener {
                context.startActivity(Intent(context, CustomCakeActivity::class.java))
            }

            imgAlert.setOnClickListener {
                context.startActivity(Intent(context, NotificationActivity::class.java))
            }
        }
    }

    fun endProgress() {
        binding.progressBar.visibility = View.GONE
        binding.mainView.visibility = View.VISIBLE
    }

}