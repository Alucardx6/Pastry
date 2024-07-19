package ir.abyx.pastry.mvp.ext

interface ViewUtils {

    fun sendComment(text: String, rate: Float, postId: Int) {}

    fun favorite(action: String, id: Int) {}

    fun setUserInfo(
        name: String,
        email: String,
        day: String,
        month: String,
        year: String,
        gender: Int
    ) {
    }

    fun setAddress(name: String, phone: String, address: String) {}

    fun editAddress(name: String, phone: String, address: String, addressId: String) {}

    fun deleteAddress(addressId: Int) {}

    fun setCart(pastryId: Int, amount: Int) {}

    fun deleteItem(itemId: Int) {}

}