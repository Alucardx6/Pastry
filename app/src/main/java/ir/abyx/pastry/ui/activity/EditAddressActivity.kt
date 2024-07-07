package ir.abyx.pastry.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.mvp.model.ModelEditAddressActivity
import ir.abyx.pastry.mvp.presenter.PresenterEditAddressActivity
import ir.abyx.pastry.mvp.view.ViewEditAddressActivity
import kotlin.properties.Delegates

class EditAddressActivity : AppCompatActivity() {

    private var state by Delegates.notNull<Boolean>()
    private var id by Delegates.notNull<Int>()
    private var receiver by Delegates.notNull<String>()
    private var phone by Delegates.notNull<String>()
    private var address by Delegates.notNull<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        state = intent.getBooleanExtra("edit", false)
        id = intent.getIntExtra("id", 0)

        receiver = if(state) intent.getStringExtra("receiver")!! else ""
        phone = if(state) intent.getStringExtra("phone")!! else ""
        address = if(state) intent.getStringExtra("address")!! else ""


        val view = ViewEditAddressActivity(
            this,
            id,
            state,
            receiver,
            phone,
            address,
            object : ActivityUtils {
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

        val presenter = PresenterEditAddressActivity(this, view, ModelEditAddressActivity())
        presenter.onCreate()
    }
}