package ir.abyx.pastry.mvp.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import ir.abyx.pastry.data.local.preferences.SecureSharePref
import ir.abyx.pastry.data.local.preferences.SharedPrefKey
import ir.abyx.pastry.databinding.ActivitySplashBinding
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.ui.activity.LoginActivity
import ir.abyx.pastry.ui.activity.MainActivity

@SuppressLint("CustomSplashScreen")
class ViewSplashActivity(private val context: Context, private val utils: ActivityUtils) {

    val binding = ActivitySplashBinding.inflate(LayoutInflater.from(context))
    private val loginState = SecureSharePref.getSharedPref(context)

    fun startActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (loginState.getBoolean(SharedPrefKey.LOGIN_STATE_KEY, false))
                context.startActivity(Intent(context, MainActivity::class.java))
            else
                context.startActivity(Intent(context, LoginActivity::class.java))

            utils.finished()
        }, 3000)
    }

}