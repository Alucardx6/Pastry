package ir.abyx.pastry.mvp.presenter

import ir.abyx.pastry.mvp.ext.BaseLifecycle
import ir.abyx.pastry.mvp.model.ModelNotificationActivity
import ir.abyx.pastry.mvp.view.ViewNotificationActivity

class PresenterNotificationActivity(
    private val view: ViewNotificationActivity,
    private val model: ModelNotificationActivity
) : BaseLifecycle {

    override fun onCreate() {
        initialize()
        view.backButton()
    }

    private fun initialize() {
        model.getNotifications()
        view.initialize()
        view.endProgress()
    }

}