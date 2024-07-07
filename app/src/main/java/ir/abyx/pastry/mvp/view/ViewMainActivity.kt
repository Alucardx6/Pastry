package ir.abyx.pastry.mvp.view

import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.FragmentManager
import ir.abyx.pastry.R
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.databinding.ActivityMainBinding
import ir.abyx.pastry.ui.customView.buttomNav.ActiveFragment
import ir.abyx.pastry.ui.customView.buttomNav.FragmentType
import ir.abyx.pastry.ui.fragment.CakeCatsFragment
import ir.abyx.pastry.ui.fragment.HomeFragment
import ir.abyx.pastry.ui.fragment.PastryCatsFragment
import ir.abyx.pastry.ui.fragment.ProfileFragment

class ViewMainActivity(
    private val context: Context,
    private val activityUtils: ActivityUtils
) {

    val binding = ActivityMainBinding.inflate(LayoutInflater.from(context))


    fun setFragment() {
        activityUtils.setFragment(HomeFragment(context, activityUtils))
    }

    fun initBottomNav() {
        binding.customBottomNavigation.onClickHelper(object : ActiveFragment {
            override fun setFragment(type: FragmentType) {
                val fragment = when (type) {
                    FragmentType.HOME -> HomeFragment(context, activityUtils)
                    FragmentType.CAKE -> CakeCatsFragment(context)
                    FragmentType.PASTRY -> PastryCatsFragment(context)
                    FragmentType.PROFILE -> ProfileFragment(context)
                }

                activityUtils.setFragment(fragment)
            }
        })
    }
}