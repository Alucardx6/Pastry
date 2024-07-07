package ir.abyx.pastry.mvp.model

import ir.abyx.pastry.data.remote.dataModel.Address
import ir.abyx.pastry.data.remote.dataModel.DefaultModel
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.mainService.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModelEditAddressActivity {

    private val service = RetrofitService.addressApiService

    fun addAddress(
        uId: String,
        pubKey: String,
        apiKey: String,
        address: String,
        receiver: String,
        phone: String,
        callbackRequest: CallbackRequest<DefaultModel>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.addAddress(uId, pubKey, apiKey, address, receiver, phone)
            callbackRequest.getResponse(response)
        }
    }

    fun editAddress(
        uId: String,
        pubKey: String,
        apiKey: String,
        address: String,
        receiver: String,
        phone: String,
        addressId: String,
        callbackRequest: CallbackRequest<DefaultModel>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.editAddress(uId, pubKey, apiKey, address, receiver, phone, addressId)
            callbackRequest.getResponse(response)
        }
    }
}