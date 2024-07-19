package ir.abyx.pastry.data.remote.apiRepository

import ir.abyx.pastry.data.remote.dataModel.AllCustomCake
import ir.abyx.pastry.data.remote.dataModel.DefaultModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CustomCakeApiService {

    @GET("cakes")
    suspend fun getCakes(
        @Header("app-api-key") apiKey: String,
        @Header("app-device-uid") id: String,
        @Header("app-public-key") pubKey: String,
    ): Response<AllCustomCake>

    @Multipart
    @POST("cake")
    suspend fun sendCakes(
        @Header("app-api-key") apiKey: String,
        @Header("app-device-uid") id: String,
        @Header("app-public-key") pubKey: String,
        @Part file: List<MultipartBody.Part>,
        @Part("description") description: RequestBody,
        @Part("weight") weight: RequestBody,
        @Part("floor") floor: RequestBody
    ): Response<DefaultModel>

}