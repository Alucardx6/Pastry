package ir.abyx.pastry.mvp.presenter

import ir.abyx.pastry.mvp.ext.BaseLifecycle
import ir.abyx.pastry.mvp.model.ModelSplashActivity
import ir.abyx.pastry.mvp.view.ViewSplashActivity

class PresenterSplashActivity(
    private val view: ViewSplashActivity,
    private val model: ModelSplashActivity
) : BaseLifecycle {

    override fun onCreate() {
        startActivity()
    }

    private fun startActivity() {
        view.startActivity()
    }
}