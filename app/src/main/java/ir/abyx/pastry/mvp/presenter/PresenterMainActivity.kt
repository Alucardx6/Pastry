package ir.abyx.pastry.mvp.presenter

import ir.abyx.pastry.mvp.ext.BaseLifecycle
import ir.abyx.pastry.mvp.model.ModelMainActivity
import ir.abyx.pastry.mvp.view.ViewMainActivity

class PresenterMainActivity(
    private val model: ModelMainActivity,
    private val view: ViewMainActivity
) : BaseLifecycle {

    override fun onCreate() {
    }
}