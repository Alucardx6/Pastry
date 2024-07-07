package ir.abyx.pastry.data.remote.apiRepository

import ir.abyx.pastry.data.remote.dataModel.Address
import ir.abyx.pastry.data.remote.dataModel.DefaultModel
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AddressApiService {

    @GET("user/address")
    suspend fun getAddress(
        @Header("app-api-key") apiKey: String,
        @Header("app-device-uid") id: String,
        @Header("app-public-key") pubKey: String
    ): Response<Address>

    @DELETE("user/address/{id}")
    suspend fun deleteAddress(
        @Header("app-device-uid") id: String,
        @Header("app-public-key") pubKey: String,
        @Header("app-api-key") apiKey: String,
        @Path(value = "id", encoded = false) ID: Int
    ): Response<Address>

    @FormUrlEncoded
    @POST("user/address/{id}")
    suspend fun editAddress(
        @Header("app-device-uid") id: String,
        @Header("app-public-key") pubKey: String,
        @Header("app-api-key") apiKey: String,
        @Field("address") address: String,
        @Field("receiver") receiver: String,
        @Field("phone") phone: String,
        @Path(value = "id", encoded = false) ID: String
    ): Response<DefaultModel>

    @FormUrlEncoded
    @POST("user/address")
    suspend fun addAddress(
        @Header("app-device-uid") id: String,
        @Header("app-public-key") pubKey: String,
        @Header("app-api-key") apiKey: String,
        @Field("address") address: String,
        @Field("receiver") receiver: String,
        @Field("phone") phone: String
    ): Response<DefaultModel>

}