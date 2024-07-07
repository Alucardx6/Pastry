package ir.abyx.pastry.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.mvp.model.ModelSplashActivity
import ir.abyx.pastry.mvp.presenter.PresenterSplashActivity
import ir.abyx.pastry.mvp.view.ViewSplashActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val view = ViewSplashActivity(this, object : ActivityUtils {
            override fun finished() {
                finish()
            }
        })

        setContentView(view.binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(view.binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val presenter = PresenterSplashActivity(view, ModelSplashActivity())
        presenter.onCreate()
    }
}