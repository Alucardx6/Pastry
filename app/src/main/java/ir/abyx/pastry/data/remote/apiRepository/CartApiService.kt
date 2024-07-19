package ir.abyx.pastry.data.remote.apiRepository

import ir.abyx.pastry.data.remote.dataModel.DefaultModel
import ir.abyx.pastry.data.remote.dataModel.MainCartModel
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface CartApiService {

    @FormUrlEncoded
    @POST("cart/add/")
    suspend fun addItem(
        @Header("app-api-key") apiKey: String,
        @Header("app-device-uid") id: String,
        @Header("app-public-key") pubKey: String,
        @Field("pastry_id") pastry_id: Int,
        @Field("amount") amount: Int,
    ): Response<DefaultModel>

    @GET("cart/")
    suspend fun getItem(
        @Header("app-api-key") apiKey: String,
        @Header("app-device-uid") id: String,
        @Header("app-public-key") pubKey: String,
    ): Response<MainCartModel>

    @DELETE("cart/{id}")
    suspend fun deleteItem(
        @Header("app-device-uid") id: String,
        @Header("app-public-key") pubKey: String,
        @Header("app-api-key") apiKey: String,
        @Path(value = "id", encoded = false) ID: Int
    ): Response<MainCartModel>

}