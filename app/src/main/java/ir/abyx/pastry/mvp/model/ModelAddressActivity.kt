package ir.abyx.pastry.mvp.model

import ir.abyx.pastry.data.remote.dataModel.Address
import ir.abyx.pastry.data.remote.dataModel.DefaultModel
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.mainService.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModelAddressActivity {

    private val service = RetrofitService.addressApiService

    fun getAddress(
        uId: String,
        pubKey: String,
        apiKey: String,
        callbackRequest: CallbackRequest<Address>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getAddress(apiKey, uId, pubKey)
            callbackRequest.getResponse(response)
        }
    }

    fun deleteAddress(
        uId: String,
        pubKey: String,
        apiKey: String,
        addressId: Int,
        callbackRequest: CallbackRequest<Address>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.deleteAddress(uId, pubKey, apiKey, addressId)
            callbackRequest.getResponse(response)
        }
    }

}