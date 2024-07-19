package ir.abyx.pastry.data.remote.mainService

import ir.abyx.pastry.data.remote.apiRepository.AddressApiService
import ir.abyx.pastry.data.remote.apiRepository.CartApiService
import ir.abyx.pastry.data.remote.apiRepository.CatsApiService
import ir.abyx.pastry.data.remote.apiRepository.CustomCakeApiService
import ir.abyx.pastry.data.remote.apiRepository.LoginApiService
import ir.abyx.pastry.data.remote.apiRepository.MainContentApiService
import ir.abyx.pastry.data.remote.apiRepository.PastryApiService
import ir.abyx.pastry.data.remote.apiRepository.UserInfoApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val URL = "https://pastry.alirezaahmadi.info/api/v1/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val loginApiService: LoginApiService = retrofit.create(LoginApiService::class.java)
    val mainContentApiService: MainContentApiService =
        retrofit.create(MainContentApiService::class.java)
    val pastryApiService: PastryApiService = retrofit.create(PastryApiService::class.java)
    val userInfoApiService: UserInfoApiService = retrofit.create(UserInfoApiService::class.java)
    val addressApiService: AddressApiService = retrofit.create(AddressApiService::class.java)
    val customCakeApiService: CustomCakeApiService =
        retrofit.create(CustomCakeApiService::class.java)
    val catsApiService: CatsApiService = retrofit.create(CatsApiService::class.java)
    val cartApiService: CartApiService = retrofit.create(CartApiService::class.java)
}