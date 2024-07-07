package ir.abyx.pastry.androidWrapper

import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

interface ActivityUtils {

    fun setFragment(fragment: Fragment) {}

    fun viewPagerFragment(
        viewPager: ViewPager2,
        data: ArrayList<String>
    ) {}

    fun finished() {}

}