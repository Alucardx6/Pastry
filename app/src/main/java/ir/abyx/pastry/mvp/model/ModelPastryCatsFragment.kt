package ir.abyx.pastry.mvp.model

import ir.abyx.pastry.data.remote.dataModel.ParentCategoryModel
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.mainService.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModelPastryCatsFragment {

    fun getPastryCats(callbackRequest: CallbackRequest<ParentCategoryModel>) {
        val service = RetrofitService.catsApiService

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getCats("pastries")
            callbackRequest.getResponse(response)
        }
    }

}