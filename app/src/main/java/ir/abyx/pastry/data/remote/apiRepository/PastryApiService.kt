package ir.abyx.pastry.data.remote.apiRepository

import ir.abyx.pastry.data.remote.dataModel.AllPastriesModel
import ir.abyx.pastry.data.remote.dataModel.DefaultModel
import ir.abyx.pastry.data.remote.dataModel.PastryMainModel
import ir.abyx.pastry.data.remote.dataModel.RequestFavorite
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PastryApiService {

    @GET("pastry/{id}")
    suspend fun getPastry(
        @Path(value = "id", encoded = false) ID: Int,
        @Header("app-device-uid") uId: String,
        @Header("app-public-key") pubKey: String,
        @Header("app-api-key") apiKey: String
    ): Response<PastryMainModel>

    @FormUrlEncoded
    @POST("pastry/{id}/operations/")
    suspend fun setPastryFavorite(
        @Path(value = "id", encoded = false) pastryId: Int,
        @Header("app-api-key") apiKey: String,
        @Header("app-device-uid") id: String,
        @Header("app-public-key") pubKey: String,
        @Field("action") action: String
    ): Response<RequestFavorite>

    @FormUrlEncoded
    @POST("comment/")
    suspend fun setPastryComment(
        @Header("app-api-key") apiKey: String,
        @Header("app-device-uid") id: String,
        @Header("app-public-key") pubKey: String,
        @Field("post_id") post_id: Int,
        @Field("content") content: String,
        @Field("rate") rate: Float
    ): Response<DefaultModel>

    @GET("pastries")
    suspend fun getFavoritePastries(
        @Header("app-api-key") apiKey: String,
        @Header("app-device-uid") id: String,
        @Header("app-public-key") pubKey: String,
        @Query("favorite") favorite: Boolean
    ): Response<AllPastriesModel>

}