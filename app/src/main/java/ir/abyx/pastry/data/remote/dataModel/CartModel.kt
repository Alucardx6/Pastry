package ir.abyx.pastry.data.remote.dataModel

data class MainCartModel(
    val message: String,
    val success: Int,
    val http_code: String,
    val cart: CartModel
)

data class CartModel(
    val ID: Int,
    val total: Int,
    val subtotal: Int,
    val quantity: Int,
    val discount: Boolean,
    val created_at: String,
    val updated_at: String,
    val items: ArrayList<ItemModel>
)

data class ItemModel(
    val ID: Int,
    val title: String,
    val quantity: Int,
    val single_price: Int,
    val single_sale_price: Int,
    val sale_bulk_price: Int,
    val thumbnail: String,
    val total: Int,
    val subtotal: Int

)