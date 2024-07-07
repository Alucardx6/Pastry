package ir.abyx.pastry.data.remote.apiRepository

import ir.abyx.pastry.data.remote.dataModel.DefaultModel
import ir.abyx.pastry.data.remote.dataModel.UserInfoData
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserInfoApiService {

    @GET("auth/heartbeat")
    suspend fun getUser(
        @Header("app-api-key") apiKey: String,
        @Header("app-device-uid") id: String,
        @Header("app-public-key") pubKey: String
    ): Response<UserInfoData>

    @FormUrlEncoded
    @POST("user/profile")
    suspend fun setUserInfo(
        @Header("app-api-key") apiKey: String,
        @Header("app-device-uid") id: String,
        @Header("app-public-key") pubKey: String,
        @Field("fullname") fullName: String,
        @Field("email") email: String,
        @Field("day") day: String,
        @Field("month") month: String,
        @Field("year") year: String,
        @Field("sex") gender: Int
    ): Response<DefaultModel>
}