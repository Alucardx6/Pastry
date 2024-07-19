package ir.abyx.pastry.mvp.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.data.remote.dataModel.CartModel
import ir.abyx.pastry.databinding.ActivityMainBinding
import ir.abyx.pastry.ui.activity.NotificationActivity
import ir.abyx.pastry.ui.customView.buttomNav.ActiveFragment
import ir.abyx.pastry.ui.customView.buttomNav.FragmentType
import ir.abyx.pastry.ui.fragment.CakeCatsFragment
import ir.abyx.pastry.ui.fragment.HomeFragment
import ir.abyx.pastry.ui.fragment.PastryCatsFragment
import ir.abyx.pastry.ui.fragment.ProfileFragment
import ir.abyx.pastry.ui.fragment.ShoppingCartFragment

class ViewMainActivity(
    private val context: Context,
    private val activityUtils: ActivityUtils
) {

    val binding = ActivityMainBinding.inflate(LayoutInflater.from(context))


    fun initialize(cart: CartModel) {
        if (cart.quantity != 0) {
            binding.customBottomNavigation.cartItems(cart.quantity.toString())
        }
    }

    fun setFragment() {
        activityUtils.setFragment(HomeFragment(context, activityUtils))
    }

    fun initAppBar() {
        binding.apply {
            customAppBar.apply {

                showNavDrawer(context)

                alert().setOnClickListener {
                    context.startActivity(Intent(context, NotificationActivity::class.java))
                }
            }
        }
    }

    fun initBottomNav() {

        binding.customBottomNavigation.onClickHelper(object : ActiveFragment {
            override fun setFragment(type: FragmentType) {
                val fragment = when (type) {
                    FragmentType.HOME -> HomeFragment(context, activityUtils)
                    FragmentType.CAKE -> CakeCatsFragment(context)
                    FragmentType.PASTRY -> PastryCatsFragment(context)
                    FragmentType.PROFILE -> ProfileFragment(context)
                    FragmentType.SHOPPING_CART -> ShoppingCartFragment(context)
                }

                activityUtils.setFragment(fragment)
            }
        })
    }
}