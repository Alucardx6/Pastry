package ir.abyx.pastry.data.remote.apiRepository

import ir.abyx.pastry.data.remote.dataModel.RequestMain
import retrofit2.Response
import retrofit2.http.GET

interface MainContentApiService {

    @GET("main")
    suspend fun getContent(): Response<RequestMain>

}