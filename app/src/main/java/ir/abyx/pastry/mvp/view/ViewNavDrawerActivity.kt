package ir.abyx.pastry.mvp.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.data.remote.dataModel.UserData
import ir.abyx.pastry.databinding.ActivityNavDrawerBinding
import ir.abyx.pastry.ui.activity.AboutActivity

class ViewNavDrawerActivity(
    private val context: Context,
    private val activityUtils: ActivityUtils
) {

    val binding = ActivityNavDrawerBinding.inflate(LayoutInflater.from(context))

    fun initialize(user: UserData) {
        binding.apply {
            txtName.text = user.fullname
            txtPhone.text = user.phone

            txtAbout.setOnClickListener {
                context.startActivity(Intent(context, AboutActivity::class.java))
                activityUtils.finished()
            }

            txtLogout.setOnClickListener {

            }

            txtOrders.setOnClickListener {

            }

            txtContact.setOnClickListener {

            }

            txtSupport.setOnClickListener {

            }

            txtUpgrade.setOnClickListener {

            }

            imgCloseNav.setOnClickListener {
                activityUtils.finished()
            }
        }
    }

}