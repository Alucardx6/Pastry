package ir.abyx.pastry.mvp.presenter

import android.content.Context
import ir.abyx.pastry.data.remote.dataModel.RequestMain
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.ext.ErrorUtils
import ir.abyx.pastry.mvp.ext.BaseLifecycle
import ir.abyx.pastry.mvp.ext.ToastUtils
import ir.abyx.pastry.mvp.model.ModelHomeFragment
import ir.abyx.pastry.mvp.view.ViewHomeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PresenterHomeFragment(
    private val view: ViewHomeFragment,
    private val model: ModelHomeFragment,
    private val context: Context
) : BaseLifecycle {

    override fun onCreate() {
        sendRequest()
    }

    private fun sendRequest() {
        model.getContent(object : CallbackRequest<RequestMain> {
            override fun getResponse(response: Response<RequestMain>) {
                if (response.isSuccessful) {
                    CoroutineScope(Dispatchers.Main).launch {
                        val data = response.body() as RequestMain
                        view.initialized(data)
                        view.endProgress()
                    }
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        val error = ErrorUtils.getError(response)
                        ToastUtils.toast(context, error)
                        view.endProgress()
                    }
                }
            }

        })
    }

}