package ir.abyx.pastry.mvp.model

import ir.abyx.pastry.data.remote.dataModel.UserInfoData
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.mainService.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModelProfileFragment {

    fun getUserInfo(
        uId: String,
        pubKey: String,
        apiKey: String,
        callbackRequest: CallbackRequest<UserInfoData>
    ) {
        val server = RetrofitService.userInfoApiService
        CoroutineScope(Dispatchers.IO).launch {
            val response = server.getUser(apiKey, uId, pubKey)
            callbackRequest.getResponse(response)
        }
    }

}