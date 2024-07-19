package ir.abyx.pastry.mvp.model

import ir.abyx.pastry.data.remote.dataModel.AllCustomCake
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.mainService.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModelCustomCakeActivity {

    fun getCustomCakes(
        uId: String,
        pubKey: String,
        apiKey: String,
        callbackRequest: CallbackRequest<AllCustomCake>
    ) {
        val service = RetrofitService.customCakeApiService
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getCakes(apiKey, uId, pubKey)
            callbackRequest.getResponse(response)
        }
    }

}