package ir.abyx.pastry.mvp.model

import ir.abyx.pastry.data.remote.dataModel.AllPastriesModel
import ir.abyx.pastry.data.remote.dataModel.ListPastriesModel
import ir.abyx.pastry.data.remote.ext.CallbackRequest
import ir.abyx.pastry.data.remote.mainService.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModelListPastryActivity(
    private val id: Int,
    private val type: String
) {

    val service = RetrofitService.pastryApiService

    fun getMainContent(
        callbackRequest: CallbackRequest<ListPastriesModel>,
        callbackRequest2: CallbackRequest<AllPastriesModel>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            if (id != 0)
                callbackRequest.getResponse(service.getPastriesList(id, true))
            else
                callbackRequest2.getResponse(service.getPastriesByType(type))
        }
    }

    fun getTitle() = when (type) {
        "NEW" -> "تازه ترین ها"
        "RATE" -> "محبوب ترین ها"
        "SPECIAL_OFFER" -> "پیشنهاد ویژه"
        else -> ""
    }
}