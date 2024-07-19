package ir.abyx.pastry.mvp.presenter

import android.content.Context
import ir.abyx.pastry.mvp.ext.BaseLifecycle
import ir.abyx.pastry.mvp.model.ModelDiscountActivity
import ir.abyx.pastry.mvp.view.ViewDiscountActivity

class PresenterDiscountActivity(
    context: Context,
    private val view: ViewDiscountActivity,
    private val model: ModelDiscountActivity
) : BaseLifecycle {

    override fun onCreate() {
        view.initialize()
        view.backButton()
    }

}