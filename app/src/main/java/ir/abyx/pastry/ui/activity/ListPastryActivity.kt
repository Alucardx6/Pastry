package ir.abyx.pastry.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.mvp.model.ModelListPastryActivity
import ir.abyx.pastry.mvp.presenter.PresenterListPastryActivity
import ir.abyx.pastry.mvp.view.ViewListPastryActivity

class ListPastryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val view = ViewListPastryActivity(this, object : ActivityUtils {
            override fun finished() {
                finish()
            }
        })

        val id = intent.getIntExtra("id", 0)
        val type = intent.getStringExtra("type") ?: ""

        setContentView(view.binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(view.binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val presenter = PresenterListPastryActivity(this, view, ModelListPastryActivity(id, type))
        presenter.onCreate()
    }
}