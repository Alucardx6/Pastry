package ir.abyx.pastry.ui.activity

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ir.abyx.pastry.R
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.mvp.model.ModelNavDrawerActivity
import ir.abyx.pastry.mvp.presenter.PresenterNavDrawerActivity
import ir.abyx.pastry.mvp.view.ViewNavDrawerActivity

class NavDrawerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)

        val view = ViewNavDrawerActivity(this, object : ActivityUtils {
            override fun finished() {
                closeNav()
            }
        })

        setContentView(view.binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(view.binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val presenter = PresenterNavDrawerActivity(this, view, ModelNavDrawerActivity())
        presenter.onCreate()

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    closeNav()
                }
            })
    }

    private fun closeNav() {
        finish()
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
    }
}