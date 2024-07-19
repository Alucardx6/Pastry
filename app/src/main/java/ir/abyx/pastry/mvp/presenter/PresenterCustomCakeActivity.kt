package ir.abyx.pastry.mvp.presenter

import android.content.Context
import ir.abyx.pastry.androidWrapper.DeviceInfo
import ir.abyx.pastry.data.remote.dataModel.AllCustomCake
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.ext.ErrorUtils
import ir.abyx.pastry.mvp.ext.BaseLifecycle
import ir.abyx.pastry.mvp.ext.ToastUtils
import ir.abyx.pastry.mvp.model.ModelCustomCakeActivity
import ir.abyx.pastry.mvp.view.ViewCustomCakeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PresenterCustomCakeActivity(
    private val context: Context,
    private val view: ViewCustomCakeActivity,
    private val model: ModelCustomCakeActivity
) : BaseLifecycle {

    override fun onCreate() {
        initialize()
        view.backButton()
    }

    private fun initialize() {
        model.getCustomCakes(
            DeviceInfo.getDeviceID(context),
            DeviceInfo.getPublicKey(context),
            DeviceInfo.getApi(context),
            object : CallbackRequest<AllCustomCake> {
                override fun getResponse(response: Response<AllCustomCake>) {
                    if (response.isSuccessful) {
                        CoroutineScope(Dispatchers.Main).launch {
                            val data = response.body() as AllCustomCake
                            view.initialize(data)
                            view.endProgress()
                        }
                    } else {
                        CoroutineScope(Dispatchers.Main).launch {
                            ToastUtils.toast(context, ErrorUtils.getError(response))
                            view.endProgress()
                        }
                    }
                }

            }
        )
    }
}