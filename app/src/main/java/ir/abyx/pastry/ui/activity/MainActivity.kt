package ir.abyx.pastry.ui.activity

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import ir.abyx.pastry.R
import ir.abyx.pastry.adapter.viewPager.CustomSliderPagerAdapter
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.mvp.model.ModelMainActivity
import ir.abyx.pastry.mvp.presenter.PresenterMainActivity
import ir.abyx.pastry.mvp.view.ViewMainActivity

class MainActivity : AppCompatActivity(), ActivityUtils {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val view = ViewMainActivity(this, this)
        setContentView(view.binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(view.binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val presenter = PresenterMainActivity(view, ModelMainActivity())
        presenter.onCreate()
    }

    override fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frameLayout, fragment)
            .commit()
    }

    override fun viewPagerFragment(
        viewPager: ViewPager2,
        data: ArrayList<String>,
    ) {
        viewPager.adapter =
            CustomSliderPagerAdapter(supportFragmentManager, lifecycle, data)
    }
}