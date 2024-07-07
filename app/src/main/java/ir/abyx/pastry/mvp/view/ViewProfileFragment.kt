package ir.abyx.pastry.mvp.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import ir.abyx.pastry.data.remote.dataModel.UserData
import ir.abyx.pastry.databinding.ProfileFragmentBinding
import ir.abyx.pastry.ui.activity.AddressActivity
import ir.abyx.pastry.ui.activity.FavoriteActivity
import ir.abyx.pastry.ui.activity.UserActivity

class ViewProfileFragment(private val context: Context) {

    val binding = ProfileFragmentBinding.inflate(LayoutInflater.from(context))

    fun initialize(data: UserData) {
        binding.txtUsername.text = data.fullname
        binding.txtPhone.text = data.phone
    }

    fun onClick() {
        binding.imgUserInfo.setOnClickListener {
            context.startActivity(Intent(context, UserActivity::class.java))
        }

        binding.imgFavorite.setOnClickListener {
            context.startActivity(Intent(context, FavoriteActivity::class.java))
        }

        binding.imgAddress.setOnClickListener {
            context.startActivity(Intent(context, AddressActivity::class.java))
        }

        binding.imgCake.setOnClickListener {
//            context.startActivity(Intent(context, CustomCakeActivity::class.java))
        }
    }

    fun endProgress() {
        binding.progressBar.visibility = View.GONE
        binding.mainView.visibility = View.VISIBLE
    }

}