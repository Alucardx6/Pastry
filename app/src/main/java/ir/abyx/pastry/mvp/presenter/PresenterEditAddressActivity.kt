package ir.abyx.pastry.mvp.presenter

import android.content.Context
import ir.abyx.pastry.androidWrapper.DeviceInfo
import ir.abyx.pastry.data.remote.dataModel.DefaultModel
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.ext.ErrorUtils
import ir.abyx.pastry.mvp.ext.BaseLifecycle
import ir.abyx.pastry.mvp.ext.ToastUtils
import ir.abyx.pastry.mvp.ext.ViewUtils
import ir.abyx.pastry.mvp.model.ModelEditAddressActivity
import ir.abyx.pastry.mvp.view.ViewEditAddressActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PresenterEditAddressActivity(
    private val context: Context,
    private val view: ViewEditAddressActivity,
    private val model: ModelEditAddressActivity
) : BaseLifecycle {

    override fun onCreate() {
        initialize()
        view.backButton()
    }

    private fun initialize() {
        view.initialize(object : ViewUtils {
            override fun setAddress(name: String, phone: String, address: String) {
                model.addAddress(DeviceInfo.getDeviceID(context),
                    DeviceInfo.getPublicKey(context),
                    DeviceInfo.getApi(context),
                    address, name, phone, object : CallbackRequest<DefaultModel> {
                        override fun getResponse(response: Response<DefaultModel>) {
                            val data: String

                            if (response.isSuccessful) {
                                val message = response.body() as DefaultModel
                                data = message.message
                            } else
                                data = ErrorUtils.getError(response)

                            CoroutineScope(Dispatchers.Main).launch {
                                ToastUtils.toast(context, data)
                                view.disableProgress()
                                view.finish()
                            }
                        }
                    })
            }

            override fun editAddress(
                name: String,
                phone: String,
                address: String,
                addressId: String
            ) {
                model.editAddress(
                    DeviceInfo.getDeviceID(context),
                    DeviceInfo.getPublicKey(context),
                    DeviceInfo.getApi(context),
                    address,
                    name,
                    phone,
                    addressId,
                    object : CallbackRequest<DefaultModel> {
                        override fun getResponse(response: Response<DefaultModel>) {
                            val data: String
                            if (response.isSuccessful) {
                                val result = response.body() as DefaultModel
                                data = result.message
                            } else
                                data = ErrorUtils.getError(response)

                            CoroutineScope(Dispatchers.Main).launch {
                                ToastUtils.toast(context, data)
                                view.disableProgress()
                                view.finish()
                            }
                        }
                    })
            }
        })
    }
}