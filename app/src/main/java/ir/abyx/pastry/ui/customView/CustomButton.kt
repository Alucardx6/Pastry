package ir.abyx.pastry.ui.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import ir.abyx.pastry.R
import ir.abyx.pastry.databinding.CustomButtonBinding

class CustomButton(
    context: Context,
    attrs: AttributeSet
) : FrameLayout(context, attrs) {

    private val binding = CustomButtonBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)

        initialize(attrs)
    }

    private fun initialize(attrs: AttributeSet) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomButton)
        val text = typeArray.getString(R.styleable.CustomButton_text)
        binding.customButton.text = text
        typeArray.recycle()
    }

    fun getView() = binding.customButton

    fun enableProgress() {
        binding.customButton.visibility = View.INVISIBLE
        binding.progressIndicator.visibility = View.VISIBLE
    }

    fun disableProgress() {
        binding.customButton.visibility = View.VISIBLE
        binding.progressIndicator.visibility = View.INVISIBLE
    }

}