package ir.abyx.pastry.data.remote.apiRepository

import ir.abyx.pastry.data.remote.dataModel.ParentCategoryModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatsApiService {

    @GET("cats")
    suspend fun getCats(@Query("pastry_type") type: String): Response<ParentCategoryModel>

}