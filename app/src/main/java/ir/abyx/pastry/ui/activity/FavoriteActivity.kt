package ir.abyx.pastry.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.mvp.model.ModelFavoriteActivity
import ir.abyx.pastry.mvp.presenter.PresenterFavoriteActivity
import ir.abyx.pastry.mvp.view.ViewFavoriteActivity

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val view = ViewFavoriteActivity(this, object : ActivityUtils {
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

        val presenter = PresenterFavoriteActivity(this, view, ModelFavoriteActivity())
        presenter.onCreate()
    }
}