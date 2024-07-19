package ir.abyx.pastry.mvp.model

import android.util.Log
import ir.abyx.pastry.data.remote.dataModel.DefaultModel
import ir.abyx.pastry.data.remote.dataModel.MainCartModel
import ir.abyx.pastry.data.remote.dataModel.PastryMainModel
import ir.abyx.pastry.data.remote.dataModel.RequestFavorite
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.mainService.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModelDetailPastryActivity(private val id: Int) {

    private val service = RetrofitService.pastryApiService

    fun addToCart(
        apiKey: String,
        uId: String,
        pubKey: String,
        pastryId: Int,
        amount: Int,
        callbackRequest: CallbackRequest<DefaultModel>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitService.cartApiService.addItem(
                apiKey,
                uId,
                pubKey,
                pastryId,
                amount
            )
            callbackRequest.getResponse(response)
        }
    }

    fun getDetailPastry(
        uId: String,
        pubKey: String,
        apiKey: String,
        callbackRequest: CallbackRequest<PastryMainModel>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getPastry(
                id,
                uId,
                pubKey,
                apiKey
            )
            callbackRequest.getResponse(response)
        }
    }

    fun sendComment(
        uId: String,
        pubKey: String,
        apiKey: String,
        content: String,
        rate: Float,
        postId: Int,
        callbackRequest: CallbackRequest<DefaultModel>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.setPastryComment(
                apiKey, uId, pubKey, postId, content, rate
            )
            callbackRequest.getResponse(response)
        }
    }

    fun setFavorite(
        postId: Int,
        uId: String,
        pubKey: String,
        apiKey: String,
        action: String,
        callbackRequest: CallbackRequest<RequestFavorite>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.setPastryFavorite(
                postId,
                apiKey, uId, pubKey, action
            )
            callbackRequest.getResponse(response)
        }
    }
}