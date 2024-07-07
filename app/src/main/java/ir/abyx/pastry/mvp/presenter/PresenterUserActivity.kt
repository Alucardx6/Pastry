package ir.abyx.pastry.mvp.presenter

import android.bluetooth.BluetoothClass.Device
import android.content.Context
import ir.abyx.pastry.androidWrapper.DeviceInfo
import ir.abyx.pastry.data.remote.dataModel.DefaultModel
import ir.abyx.pastry.data.remote.dataModel.UserInfoData
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.ext.ErrorUtils
import ir.abyx.pastry.mvp.ext.BaseLifecycle
import ir.abyx.pastry.mvp.ext.ToastUtils
import ir.abyx.pastry.mvp.ext.ViewUtils
import ir.abyx.pastry.mvp.model.ModelUserActivity
import ir.abyx.pastry.mvp.view.ViewUserActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PresenterUserActivity(
    private val context: Context,
    private val view: ViewUserActivity,
    private val model: ModelUserActivity
) : BaseLifecycle {

    override fun onCreate() {
        initialize()
        view.backButton()
    }

    private fun initialize() {
        model.getUserData(
            DeviceInfo.getDeviceID(context),
            DeviceInfo.getPublicKey(context),
            DeviceInfo.getApi(context),
            object : CallbackRequest<UserInfoData> {
                override fun getResponse(response: Response<UserInfoData>) {
                    if (response.isSuccessful) {
                        CoroutineScope(Dispatchers.Main).launch {
                            val data = response.body() as UserInfoData
                            view.initialize(data.user, object : ViewUtils {
                                override fun setUserInfo(
                                    name: String,
                                    email: String,
                                    day: String,
                                    month: String,
                                    year: String,
                                    gender: Int
                                ) {
                                    model.setUserInfo(
                                        DeviceInfo.getDeviceID(context),
                                        DeviceInfo.getPublicKey(context),
                                        DeviceInfo.getApi(context),
                                        name,
                                        email,
                                        day,
                                        month,
                                        year,
                                        gender,
                                        object : CallbackRequest<DefaultModel> {
                                            override fun getResponse(response: Response<DefaultModel>) {
                                                if (response.isSuccessful) {
                                                    CoroutineScope(Dispatchers.Main).launch {
                                                        val message =
                                                            response.body() as DefaultModel
                                                        ToastUtils.toast(context, message.message)
                                                        view.endSaveInfo()
                                                    }
                                                } else {
                                                    CoroutineScope(Dispatchers.Main).launch {
                                                        ToastUtils.toast(
                                                            context,
                                                            ErrorUtils.getError(response)
                                                        )
                                                        view.endSaveInfo()
                                                    }
                                                }
                                            }
                                        })
                                }

                            })
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