package ir.abyx.pastry.ui.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import ir.abyx.pastry.R
import ir.abyx.pastry.databinding.CustomBottomNavigationBinding

class CustomBottomNavigation(
    context: Context,
    attrs: AttributeSet
) : FrameLayout(context, attrs) {

    private val binding = CustomBottomNavigationBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)

        initialize(attrs)
    }

    private fun initialize(attrs: AttributeSet) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomBottomNavigation)

        typeArray.recycle()
    }

}