package ir.abyx.pastry.data.remote.dataModel

data class AllCustomCake(
    val message: String,
    val page: Int,
    val pages: Int,
    val total: Int,
    val custom_cakes: ArrayList<CustomCake>
)

data class CustomCake(
    val ID: Int,
    val description: String,
    val weight: String,
    val floor: String,
    val files: String,
    val user: String
)
