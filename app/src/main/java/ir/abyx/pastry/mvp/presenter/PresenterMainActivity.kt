package ir.abyx.pastry.mvp.presenter

import android.content.Context
import ir.abyx.pastry.androidWrapper.DeviceInfo
import ir.abyx.pastry.data.remote.dataModel.MainCartModel
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.mvp.ext.BaseLifecycle
import ir.abyx.pastry.mvp.model.ModelMainActivity
import ir.abyx.pastry.mvp.view.ViewMainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PresenterMainActivity(
    private val context: Context,
    private val view: ViewMainActivity,
    private val model: ModelMainActivity
) : BaseLifecycle {

    override fun onCreate() {
        initialize()
        view.setFragment()
        view.initAppBar()
        bottomNavClick()
    }

    override fun onResume() {
        initialize()
    }

    private fun initialize() {
        model.getCart(
            DeviceInfo.getApi(context),
            DeviceInfo.getDeviceID(context),
            DeviceInfo.getPublicKey(context),
            object : CallbackRequest<MainCartModel> {
                override fun getResponse(response: Response<MainCartModel>) {
                    if (response.isSuccessful) {
                        CoroutineScope(Dispatchers.Main).launch {
                            val data = response.body() as MainCartModel
                            view.initialize(data.cart)
                        }
                    }
                }
            })
    }

    private fun bottomNavClick() {
        view.initBottomNav()
    }
}