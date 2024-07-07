package ir.abyx.pastry.ui.customView.buttomNav

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import ir.abyx.pastry.R
import ir.abyx.pastry.databinding.CustomBottomNavigationBinding

class CustomBottomNavigation(
    context: Context,
    attrs: AttributeSet
) : FrameLayout(context, attrs) {

    private val binding = CustomBottomNavigationBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
    }

    fun onClickHelper(activeFragment: ActiveFragment) {
        binding.viewGroup.setOnClickListener {
            activeHome()
            activeFragment.setFragment(FragmentType.HOME)
        }

        binding.viewCake.setOnClickListener {
            activeCake()
            activeFragment.setFragment(FragmentType.CAKE)
        }

        binding.viewPastry.setOnClickListener {
            activePastry()
            activeFragment.setFragment(FragmentType.PASTRY)
        }

        binding.viewProfile.setOnClickListener {
            activeProfile()
            activeFragment.setFragment(FragmentType.PROFILE)
        }
    }

    private fun activeHome() {
        binding.viewGroup.setBackgroundResource(R.drawable.nav_item_select)
        binding.viewPastry.background = null
        binding.viewCake.background = null
        binding.viewProfile.background = null
    }

    private fun activeCake() {
        binding.viewCake.setBackgroundResource(R.drawable.nav_item_select)
        binding.viewPastry.background = null
        binding.viewGroup.background = null
        binding.viewProfile.background = null
    }

    private fun activePastry() {
        binding.viewPastry.setBackgroundResource(R.drawable.nav_item_select)
        binding.viewCake.background = null
        binding.viewGroup.background = null
        binding.viewProfile.background = null
    }

    private fun activeProfile() {
        binding.viewProfile.setBackgroundResource(R.drawable.nav_item_select)
        binding.viewCake.background = null
        binding.viewGroup.background = null
        binding.viewPastry.background = null
    }

}