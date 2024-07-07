package ir.abyx.pastry.mvp.presenter

import android.content.Context
import ir.abyx.pastry.androidWrapper.DeviceInfo
import ir.abyx.pastry.data.remote.dataModel.AllPastriesModel
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.ext.ErrorUtils
import ir.abyx.pastry.mvp.ext.BaseLifecycle
import ir.abyx.pastry.mvp.ext.ToastUtils
import ir.abyx.pastry.mvp.model.ModelFavoriteActivity
import ir.abyx.pastry.mvp.view.ViewFavoriteActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PresenterFavoriteActivity(
    private val context: Context,
    private val view: ViewFavoriteActivity,
    private val model: ModelFavoriteActivity
) : BaseLifecycle {

    override fun onCreate() {
        initialize()
        view.backButton()
    }

    private fun initialize() {
        model.getPastries(
            DeviceInfo.getDeviceID(context),
            DeviceInfo.getPublicKey(context),
            DeviceInfo.getApi(context),
            object : CallbackRequest<AllPastriesModel> {
                override fun getResponse(response: Response<AllPastriesModel>) {
                    if (response.isSuccessful) {
                        CoroutineScope(Dispatchers.Main).launch {
                            val data = response.body() as AllPastriesModel
                            view.initialize(data.pastries)
                            view.endProgress()
                        }
                    } else {
                        CoroutineScope(Dispatchers.Main).launch {
                            ToastUtils.toast(context, ErrorUtils.getError(response))
                            view.endProgress()
                        }
                    }
                }

            })
    }

}