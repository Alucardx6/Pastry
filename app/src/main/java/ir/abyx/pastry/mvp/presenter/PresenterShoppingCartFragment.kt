package ir.abyx.pastry.mvp.presenter

import android.content.Context
import android.util.Log
import ir.abyx.pastry.androidWrapper.DeviceInfo
import ir.abyx.pastry.data.remote.dataModel.Address
import ir.abyx.pastry.data.remote.dataModel.DefaultModel
import ir.abyx.pastry.data.remote.dataModel.MainCartModel
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.ext.ErrorUtils
import ir.abyx.pastry.mvp.ext.BaseLifecycle
import ir.abyx.pastry.mvp.ext.ToastUtils
import ir.abyx.pastry.mvp.ext.ViewUtils
import ir.abyx.pastry.mvp.model.ModelShoppingCartFragment
import ir.abyx.pastry.mvp.view.ViewShoppingCartFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PresenterShoppingCartFragment(
    private val context: Context,
    private val view: ViewShoppingCartFragment,
    private val model: ModelShoppingCartFragment
) : BaseLifecycle {

    override fun onCreate() {
        initialize()
        rbPost()
    }

    override fun onResume() {
        model.getAddress(
            DeviceInfo.getApi(context),
            DeviceInfo.getDeviceID(context),
            DeviceInfo.getPublicKey(context),
            object : CallbackRequest<Address> {
                override fun getResponse(response: Response<Address>) {
                    if (response.isSuccessful) {
                        CoroutineScope(Dispatchers.Main).launch {
                            val result = response.body() as Address
                            try {
                                view.updateRecyclerAddress(result)
                            } catch (e: Exception) {
                                Log.i("Error", e.message.toString())
                            }
                        }
                    } else
                        CoroutineScope(Dispatchers.Main).launch {
                            ToastUtils.toast(context, ErrorUtils.getError(response))
                        }
                }

            }
        )
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
                            view.initialize(data.cart, object : ViewUtils {
                                override fun deleteItem(itemId: Int) {
                                    model.deleteItem(
                                        DeviceInfo.getApi(context),
                                        DeviceInfo.getDeviceID(context),
                                        DeviceInfo.getPublicKey(context),
                                        itemId,
                                        object : CallbackRequest<MainCartModel> {
                                            override fun getResponse(response: Response<MainCartModel>) {
                                                if (response.isSuccessful) {
                                                    CoroutineScope(Dispatchers.Main).launch {
                                                        val result =
                                                            response.body() as MainCartModel
                                                        ToastUtils.toast(context, result.message)
                                                        view.updateRecycler(result.cart)
                                                    }
                                                } else
                                                    CoroutineScope(Dispatchers.Main).launch {
                                                        ToastUtils.toast(
                                                            context,
                                                            ErrorUtils.getError(response)
                                                        )
                                                    }
                                            }
                                        }
                                    )
                                }
                            })
                            view.endProgress()
                        }
                    } else
                        CoroutineScope(Dispatchers.Main).launch {
                            ToastUtils.toast(context, ErrorUtils.getError(response))
                            view.endProgress()
                        }
                }

            }
        )
    }

    private fun rbPost() {
        model.getAddress(
            DeviceInfo.getApi(context),
            DeviceInfo.getDeviceID(context),
            DeviceInfo.getPublicKey(context),
            object : CallbackRequest<Address> {
                override fun getResponse(response: Response<Address>) {
                    if (response.isSuccessful) {
                        CoroutineScope(Dispatchers.Main).launch {
                            val result = response.body() as Address
                            view.rbPost(result)
                        }
                    } else
                        CoroutineScope(Dispatchers.Main).launch {
                            ToastUtils.toast(context, ErrorUtils.getError(response))
                        }
                }

            }
        )
    }
}