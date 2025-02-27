package ir.abyx.pastry.mvp.presenter

import ir.abyx.pastry.mvp.ext.BaseLifecycle
import ir.abyx.pastry.mvp.model.ModelLoginActivity
import ir.abyx.pastry.mvp.view.ViewLoginActivity

class PresenterLoginActivity(
    private val view: ViewLoginActivity,
    private val model: ModelLoginActivity
) : BaseLifecycle {

    override fun onCreate() {
        login()
        sendDeviceInfo()
    }

    private fun sendDeviceInfo() {
        view.setDeviceInfo(model.getDeviceInfo())
    }

    private fun login() {
        view.onClick(model.getUID(), model.getPublicKey())
    }

}