package ir.abyx.pastry.ui.customView

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import ir.abyx.pastry.R
import ir.abyx.pastry.databinding.CustomAppBarBinding
import ir.abyx.pastry.ui.activity.NavDrawerActivity

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

    fun notificationAlert(number: String) {
        binding.groupAlert.visibility = View.VISIBLE
        binding.txtAlertNumber.text = number
    }

    fun alert() = binding.icAlert

    fun showNavDrawer(context: Context) {
        binding.icMenu.setOnClickListener {
            context.startActivity(Intent(context, NavDrawerActivity::class.java))
        }
    }

    fun hideAlert() {
        binding.icAlert.visibility = View.GONE
    }

    fun getBackIcon() = binding.icBack

}