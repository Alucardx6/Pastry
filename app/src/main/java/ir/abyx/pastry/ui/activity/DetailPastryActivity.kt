package ir.abyx.pastry.ui.activity

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import ir.abyx.pastry.adapter.viewPager.CustomSliderPagerAdapter
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.mvp.model.ModelDetailPastryActivity
import ir.abyx.pastry.mvp.presenter.PresenterDetailPastryActivity
import ir.abyx.pastry.mvp.view.ViewDetailPastryActivity

class DetailPastryActivity : AppCompatActivity(), ActivityUtils {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val id = intent.getIntExtra("id", 0)

        val view = ViewDetailPastryActivity(this, this)

        setContentView(view.binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(view.binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val presenter = PresenterDetailPastryActivity(this, view, ModelDetailPastryActivity(id))
        presenter.onCreate()
    }

    override fun viewPagerFragment(
        viewPager: ViewPager2,
        data: ArrayList<String>,
    ) {
        viewPager.adapter =
            CustomSliderPagerAdapter(
                supportFragmentManager,
                lifecycle,
                data
            )
    }

    override fun finished() {
        finish()
    }
}