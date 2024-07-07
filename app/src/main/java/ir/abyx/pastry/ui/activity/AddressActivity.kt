package ir.abyx.pastry.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.mvp.model.ModelAddressActivity
import ir.abyx.pastry.mvp.presenter.PresenterAddressActivity
import ir.abyx.pastry.mvp.view.ViewAddressActivity

class AddressActivity : AppCompatActivity() {

    private lateinit var presenter: PresenterAddressActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val view = ViewAddressActivity(this, object : ActivityUtils {
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

        presenter = PresenterAddressActivity(this, view, ModelAddressActivity())
        presenter.onCreate()
    }

    override fun onResume() {
        super.onResume()
        presenter.onStart()
    }
}