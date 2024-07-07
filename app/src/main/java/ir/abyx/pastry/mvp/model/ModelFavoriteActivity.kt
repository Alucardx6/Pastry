package ir.abyx.pastry.mvp.model

import ir.abyx.pastry.data.remote.dataModel.AllPastriesModel
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.mainService.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModelFavoriteActivity {

    fun getPastries(
        uId: String,
        pubKey: String,
        apiKey: String,
        callbackRequest: CallbackRequest<AllPastriesModel>
    ) {
        val server = RetrofitService.pastryApiService

        CoroutineScope(Dispatchers.IO).launch {
            val response = server.getFavoritePastries(apiKey, uId, pubKey, true)
            callbackRequest.getResponse(response)
        }

    }

}