package ir.abyx.pastry.ui.customView.buttomNav

enum class FragmentType {
    HOME, CAKE, PASTRY, PROFILE, SHOPPING_CART
}

interface ActiveFragment {
    fun setFragment(type: FragmentType)
}