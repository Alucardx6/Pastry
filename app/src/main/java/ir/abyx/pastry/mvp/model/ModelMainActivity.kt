package ir.abyx.pastry.mvp.model

import ir.abyx.pastry.data.remote.dataModel.MainCartModel
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.mainService.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModelMainActivity {

    fun getCart(
        apiKey: String,
        uId: String,
        pubKey: String,
        callbackRequest: CallbackRequest<MainCartModel>
    ) {
        val service = RetrofitService.cartApiService
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getItem(apiKey, uId, pubKey)
            callbackRequest.getResponse(response)
        }
    }

}