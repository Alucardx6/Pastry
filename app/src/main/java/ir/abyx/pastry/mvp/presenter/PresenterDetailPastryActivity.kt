package ir.abyx.pastry.mvp.presenter

import android.content.Context
import android.util.Log
import ir.abyx.pastry.androidWrapper.DeviceInfo
import ir.abyx.pastry.data.remote.dataModel.DefaultModel
import ir.abyx.pastry.data.remote.dataModel.PastryMainModel
import ir.abyx.pastry.data.remote.dataModel.RequestFavorite
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.ext.ErrorUtils
import ir.abyx.pastry.mvp.ext.BaseLifecycle
import ir.abyx.pastry.mvp.ext.ToastUtils
import ir.abyx.pastry.mvp.ext.ViewUtils
import ir.abyx.pastry.mvp.model.ModelDetailPastryActivity
import ir.abyx.pastry.mvp.view.ViewDetailPastryActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PresenterDetailPastryActivity(
    private val context: Context,
    private val view: ViewDetailPastryActivity,
    private val model: ModelDetailPastryActivity
) : BaseLifecycle {

    override fun onCreate() {
        view.startGetData()
        view.onBackPressed()

        initializeView()
    }

    private fun initializeView() {
        model.getDetailPastry(
            DeviceInfo.getDeviceID(context),
            DeviceInfo.getPublicKey(context),
            DeviceInfo.getApi(context),
            object : CallbackRequest<PastryMainModel> {
                override fun getResponse(response: Response<PastryMainModel>) {
                    if (response.isSuccessful) {
                        CoroutineScope(Dispatchers.Main).launch {
                            val data = response.body() as PastryMainModel
                            view.initialize(data.pastry, object : ViewUtils {
                                override fun sendComment(text: String, rate: Float, postId: Int) {
                                    model.sendComment(
                                        DeviceInfo.getDeviceID(context),
                                        DeviceInfo.getPublicKey(context),
                                        DeviceInfo.getApi(context),
                                        text,
                                        rate,
                                        postId,
                                        object : CallbackRequest<DefaultModel> {
                                            override fun getResponse(response: Response<DefaultModel>) {
                                                if (response.isSuccessful) {
                                                    CoroutineScope(Dispatchers.Main).launch {
                                                        val result = response.body() as DefaultModel
                                                        ToastUtils.toast(context, result.message)
                                                        view.endBtnCommentProgress()
                                                    }
                                                } else {
                                                    CoroutineScope(Dispatchers.Main).launch {
                                                        val error = ErrorUtils.getError(response)
                                                        ToastUtils.toast(context, error)
                                                        view.endBtnCommentProgress()
                                                    }
                                                }
                                            }

                                        }
                                    )
                                }

                                override fun favorite(action: String, id: Int) {
                                    model.setFavorite(id,
                                        DeviceInfo.getDeviceID(context),
                                        DeviceInfo.getPublicKey(context),
                                        DeviceInfo.getApi(context),
                                        action,
                                        object : CallbackRequest<RequestFavorite> {
                                            override fun getResponse(response: Response<RequestFavorite>) {
                                                if (response.isSuccessful) {
                                                    CoroutineScope(Dispatchers.Main).launch {
                                                        val favoriteResult =
                                                            response.body() as RequestFavorite
                                                        ToastUtils.toast(
                                                            context,
                                                            favoriteResult.message
                                                        )
                                                    }
                                                } else {
                                                    CoroutineScope(Dispatchers.Main).launch {
                                                        val error = ErrorUtils.getError(response)
                                                        ToastUtils.toast(context, error)
                                                    }
                                                }
                                            }
                                        })
                                }

                                override fun setCart(pastryId: Int, amount: Int) {
                                    model.addToCart(DeviceInfo.getApi(context),
                                        DeviceInfo.getDeviceID(context),
                                        DeviceInfo.getPublicKey(context),
                                        pastryId,
                                        amount,
                                        object : CallbackRequest<DefaultModel> {
                                            override fun getResponse(response: Response<DefaultModel>) {
                                                if (response.isSuccessful)
                                                    CoroutineScope(Dispatchers.Main).launch {
                                                        val result =
                                                            response.body() as DefaultModel
                                                        ToastUtils.toast(context, result.message)
                                                        view.closeDialog()
                                                    }
                                                else
                                                    CoroutineScope(Dispatchers.Main).launch {
                                                        ToastUtils.toast(
                                                            context,
                                                            ErrorUtils.getError(response)
                                                        )
                                                        view.closeDialog()
                                                    }
                                            }

                                        })
                                }
                            })
                            view.endGetData()
                        }
                    } else {
                        CoroutineScope(Dispatchers.Main).launch {
                            val error = ErrorUtils.getError(response)
                            ToastUtils.toast(context, error)
                            view.endProgress()
                        }
                    }
                }

            }
        )
    }

}