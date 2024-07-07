package ir.abyx.pastry.ui.customView.buttomNav

enum class FragmentType {
    HOME, CAKE, PASTRY, PROFILE
}

interface ActiveFragment {
    fun setFragment(type: FragmentType)
}