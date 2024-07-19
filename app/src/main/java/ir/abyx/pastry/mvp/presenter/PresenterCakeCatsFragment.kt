package ir.abyx.pastry.mvp.presenter

import android.content.Context
import ir.abyx.pastry.data.remote.dataModel.ParentCategoryModel
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.ext.ErrorUtils
import ir.abyx.pastry.mvp.ext.BaseLifecycle
import ir.abyx.pastry.mvp.ext.ToastUtils
import ir.abyx.pastry.mvp.model.ModelCakeCatsFragment
import ir.abyx.pastry.mvp.view.ViewCakeCatsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PresenterCakeCatsFragment(
    private val context: Context,
    private val view: ViewCakeCatsFragment,
    private val model: ModelCakeCatsFragment
) : BaseLifecycle {

    override fun onCreate() {
        initialize()
    }

    private fun initialize() {
        model.getCats(object : CallbackRequest<ParentCategoryModel> {
            override fun getResponse(response: Response<ParentCategoryModel>) {
                if (response.isSuccessful)
                    CoroutineScope(Dispatchers.Main).launch {
                        val result = response.body() as ParentCategoryModel
                        view.initialize(result)
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