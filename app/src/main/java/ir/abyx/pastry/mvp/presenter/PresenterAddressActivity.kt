package ir.abyx.pastry.mvp.presenter

import android.content.Context
import ir.abyx.pastry.androidWrapper.DeviceInfo
import ir.abyx.pastry.data.remote.dataModel.Address
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.ext.ErrorUtils
import ir.abyx.pastry.mvp.ext.BaseLifecycle
import ir.abyx.pastry.mvp.ext.ToastUtils
import ir.abyx.pastry.mvp.ext.ViewUtils
import ir.abyx.pastry.mvp.model.ModelAddressActivity
import ir.abyx.pastry.mvp.view.ViewAddressActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PresenterAddressActivity(
    private val context: Context,
    private val view: ViewAddressActivity,
    private val model: ModelAddressActivity
) : BaseLifecycle {

    override fun onCreate() {
        view.backButton()
    }

    private fun initialize() {
        model.getAddress(
            DeviceInfo.getDeviceID(context),
            DeviceInfo.getPublicKey(context),
            DeviceInfo.getApi(context),
            object : CallbackRequest<Address> {
                override fun getResponse(response: Response<Address>) {
                    if (response.isSuccessful) {
                        CoroutineScope(Dispatchers.Main).launch {
                            val data = response.body() as Address
                            view.initialize(data, object : ViewUtils {
                                override fun deleteAddress(addressId: Int) {
                                    model.deleteAddress(
                                        DeviceInfo.getDeviceID(context),
                                        DeviceInfo.getPublicKey(context),
                                        DeviceInfo.getApi(context),
                                        addressId, object : CallbackRequest<Address> {
                                            override fun getResponse(response: Response<Address>) {
                                                if (response.isSuccessful) {
                                                    CoroutineScope(Dispatchers.Main).launch {
                                                        val result = response.body() as Address
                                                        ToastUtils.toast(context, result.message)
                                                        view.updateRecycler(result)
                                                    }
                                                } else {
                                                    CoroutineScope(Dispatchers.Main).launch {
                                                        ToastUtils.toast(
                                                            context,
                                                            ErrorUtils.getError(response)
                                                        )
                                                    }
                                                }
                                            }

                                        }
                                    )
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

    override fun onStart() {
        initialize()
    }
}