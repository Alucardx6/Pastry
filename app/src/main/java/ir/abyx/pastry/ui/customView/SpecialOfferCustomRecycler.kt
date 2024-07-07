package ir.abyx.pastry.ui.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import ir.abyx.pastry.databinding.SpecialOfferRecyclerBinding

class SpecialOfferCustomRecycler(context: Context, attrs: AttributeSet) :
    FrameLayout(
        context,
        attrs
    ) {

    private val binding = SpecialOfferRecyclerBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
    }

    fun getRecycler() = binding.recyclerView
}