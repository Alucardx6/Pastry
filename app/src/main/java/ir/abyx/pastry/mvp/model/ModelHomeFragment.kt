package ir.abyx.pastry.mvp.model

import ir.abyx.pastry.data.remote.dataModel.RequestMain
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.mainService.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModelHomeFragment {

    fun getContent(callbackRequest: CallbackRequest<RequestMain>) {
        val service = RetrofitService.mainContentApiService
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getContent()
            callbackRequest.getResponse(response)
        }
    }
}