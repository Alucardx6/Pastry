package ir.abyx.pastry.ui.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import ir.abyx.pastry.R
import ir.abyx.pastry.databinding.CustomAppBarBinding

class CustomAppBar(
    context: Context,
    attrs: AttributeSet
) : FrameLayout(context, attrs) {

    private val binding = CustomAppBarBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)

        initialize(attrs)
    }

    private fun initialize(attrs: AttributeSet) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomAppBar)

        val back = typeArray.getBoolean(R.styleable.CustomAppBar_backIcon, false)

        if (back)
            binding.icBack.visibility = View.VISIBLE


        typeArray.recycle()
    }

    fun getBackIcon() = binding.icBack

}