package ir.abyx.pastry.mvp.model

import ir.abyx.pastry.data.remote.dataModel.Address
import ir.abyx.pastry.data.remote.dataModel.MainCartModel
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.mainService.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModelShoppingCartFragment {

    val service = RetrofitService.cartApiService

    fun getCart(
        apiKey: String,
        uId: String,
        pubKey: String,
        callbackRequest: CallbackRequest<MainCartModel>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getItem(apiKey, uId, pubKey)
            callbackRequest.getResponse(response)
        }
    }

    fun deleteItem(
        apiKey: String,
        uId: String,
        pubKey: String,
        itemId: Int,
        callbackRequest: CallbackRequest<MainCartModel>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.deleteItem(uId, pubKey, apiKey, itemId)
            callbackRequest.getResponse(response)
        }
    }

    fun getAddress(
        apiKey: String,
        uId: String,
        pubKey: String,
        callbackRequest: CallbackRequest<Address>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitService.addressApiService.getAddress(apiKey, uId, pubKey)
            callbackRequest.getResponse(response)
        }
    }

}