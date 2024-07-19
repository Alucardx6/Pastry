package ir.abyx.pastry.mvp.presenter

import android.content.Context
import ir.abyx.pastry.data.remote.dataModel.AllPastriesModel
import ir.abyx.pastry.data.remote.dataModel.ListPastriesModel
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.ext.ErrorUtils
import ir.abyx.pastry.mvp.ext.BaseLifecycle
import ir.abyx.pastry.mvp.ext.ToastUtils
import ir.abyx.pastry.mvp.model.ModelListPastryActivity
import ir.abyx.pastry.mvp.view.ViewListPastryActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PresenterListPastryActivity(
    private val context: Context,
    private val view: ViewListPastryActivity,
    private val model: ModelListPastryActivity
) : BaseLifecycle {

    override fun onCreate() {
        model.getMainContent(object : CallbackRequest<ListPastriesModel> {
            override fun getResponse(response: Response<ListPastriesModel>) {
                if (response.isSuccessful)
                    CoroutineScope(Dispatchers.Main).launch {
                        val data = response.body() as ListPastriesModel
                        view.initialize(data)
                        view.endProgress()
                    }
                else
                    CoroutineScope(Dispatchers.Main).launch {
                        ToastUtils.toast(context, ErrorUtils.getError(response))
                        view.endProgress()
                    }
            }
        }, object : CallbackRequest<AllPastriesModel> {
            override fun getResponse(response: Response<AllPastriesModel>) {
                if (response.isSuccessful)
                    CoroutineScope(Dispatchers.Main).launch {
                        val data = response.body() as AllPastriesModel
                        view.initialize2(data, model.getTitle())
                        view.endProgress()
                    }
                else
                    CoroutineScope(Dispatchers.Main).launch {
                        ToastUtils.toast(context, ErrorUtils.getError(response))
                        view.endProgress()
                    }
            }

        })
    }

}