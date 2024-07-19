package ir.abyx.pastry.mvp.presenter

import android.content.Context
import ir.abyx.pastry.androidWrapper.DeviceInfo
import ir.abyx.pastry.data.remote.dataModel.UserInfoData
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.ext.ErrorUtils
import ir.abyx.pastry.mvp.ext.BaseLifecycle
import ir.abyx.pastry.mvp.ext.ToastUtils
import ir.abyx.pastry.mvp.model.ModelNavDrawerActivity
import ir.abyx.pastry.mvp.view.ViewNavDrawerActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PresenterNavDrawerActivity(
    private val context: Context,
    private val view: ViewNavDrawerActivity,
    private val model: ModelNavDrawerActivity
) : BaseLifecycle {

    override fun onCreate() {
        initialize()
    }

    private fun initialize() {
        model.getUserInfo(
            DeviceInfo.getApi(context),
            DeviceInfo.getDeviceID(context),
            DeviceInfo.getPublicKey(context),
            object : CallbackRequest<UserInfoData> {
                override fun getResponse(response: Response<UserInfoData>) {
                    if (response.isSuccessful)
                        CoroutineScope(Dispatchers.Main).launch {
                            val data = response.body() as UserInfoData
                            view.initialize(data.user)
                        }
                    else
                        CoroutineScope(Dispatchers.Main).launch {
                            ToastUtils.toast(context, ErrorUtils.getError(response))
                        }
                }

            }
        )
    }
}