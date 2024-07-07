package ir.abyx.pastry.data.remote.ext

import retrofit2.Response

interface CallbackRequest<T> {
    fun getResponse(response: Response<T>)
}