package ir.abyx.pastry.mvp.presenter

import android.content.Context
import ir.abyx.pastry.data.remote.dataModel.ParentCategoryModel
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.ext.ErrorUtils
import ir.abyx.pastry.mvp.ext.BaseLifecycle
import ir.abyx.pastry.mvp.ext.ToastUtils
import ir.abyx.pastry.mvp.model.ModelPastryCatsFragment
import ir.abyx.pastry.mvp.view.ViewPastryCatsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PresenterPastryCatsFragment(
    private val context: Context,
    private val view: ViewPastryCatsFragment,
    private val model: ModelPastryCatsFragment
) : BaseLifecycle {

    override fun onCreate() {
        model.getPastryCats(object : CallbackRequest<ParentCategoryModel> {
            override fun getResponse(response: Response<ParentCategoryModel>) {
                if (response.isSuccessful)
                    CoroutineScope(Dispatchers.Main).launch {
                        val data = response.body() as ParentCategoryModel
                        view.initialize(data)
                        view.endProgress()
                    }
                else
                    CoroutineScope(Dispatchers.Main).launch {
                        ToastUtils.toast(context, ErrorUtils.getError(response))
                    }
            }
        })
    }

}