package ir.abyx.pastry.mvp.model

import android.util.Log
import ir.abyx.pastry.data.remote.dataModel.DefaultModel
import ir.abyx.pastry.data.remote.dataModel.UserInfoData
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.mainService.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModelUserActivity {

    private val service = RetrofitService.userInfoApiService

    fun getUserData(
        uId: String,
        pubKey: String,
        apiKey: String,
        callbackRequest: CallbackRequest<UserInfoData>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getUser(
                apiKey, uId, pubKey
            )

            callbackRequest.getResponse(response)
        }
    }

    fun setUserInfo(
        uId: String,
        pubKey: String,
        apiKey: String,
        name: String,
        email: String,
        day: String,
        month: String,
        year: String,
        gender: Int,
        callbackRequest: CallbackRequest<DefaultModel>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response =
                service.setUserInfo(apiKey, uId, pubKey, name, email, day, month, year, gender)
            callbackRequest.getResponse(response)
        }
    }

}