package ir.abyx.pastry.mvp.presenter

import ir.abyx.pastry.mvp.ext.BaseLifecycle
import ir.abyx.pastry.mvp.model.ModelMainActivity
import ir.abyx.pastry.mvp.view.ViewMainActivity

class PresenterMainActivity(
    private val view: ViewMainActivity,
    private val model: ModelMainActivity
) : BaseLifecycle {

    override fun onCreate() {
        view.setFragment()
        bottomNavClick()
    }

    private fun bottomNavClick() {
        view.initBottomNav()
    }
}