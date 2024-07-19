package ir.abyx.pastry.mvp.model

import ir.abyx.pastry.data.remote.dataModel.UserInfoData
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.mainService.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModelNavDrawerActivity {

    fun getUserInfo(
        apiKey: String,
        uId: String,
        pubKey: String,
        callbackRequest: CallbackRequest<UserInfoData>
    ) {
        val service = RetrofitService.userInfoApiService

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getUser(apiKey, uId, pubKey)
            callbackRequest.getResponse(response)
        }
    }

}