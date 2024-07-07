package ir.abyx.pastry.mvp.presenter

import android.content.Context
import ir.abyx.pastry.androidWrapper.DeviceInfo
import ir.abyx.pastry.data.remote.dataModel.UserInfoData
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.ext.ErrorUtils
import ir.abyx.pastry.mvp.ext.BaseLifecycle
import ir.abyx.pastry.mvp.ext.ToastUtils
import ir.abyx.pastry.mvp.model.ModelProfileFragment
import ir.abyx.pastry.mvp.view.ViewProfileFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PresenterProfileFragment(
    private val context: Context,
    private val view: ViewProfileFragment,
    private val model: ModelProfileFragment
) : BaseLifecycle {

    override fun onCreate() {
        initialize()
        view.onClick()
    }

    private fun initialize() {
        model.getUserInfo(
            DeviceInfo.getDeviceID(context),
            DeviceInfo.getPublicKey(context),
            DeviceInfo.getApi(context),
            object : CallbackRequest<UserInfoData> {
                override fun getResponse(response: Response<UserInfoData>) {
                    if (response.isSuccessful) {
                        CoroutineScope(Dispatchers.Main).launch {
                            val data = response.body() as UserInfoData
                            view.initialize(data.user)
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