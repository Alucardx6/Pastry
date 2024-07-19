package ir.abyx.pastry.ui.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import ir.abyx.pastry.R
import ir.abyx.pastry.databinding.CustomMainRecyclerHorizontalBinding

class CustomRecyclerHorizontal(context: Context, attrs: AttributeSet) :
    FrameLayout(
        context,
        attrs
    ) {

    private val binding = CustomMainRecyclerHorizontalBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)

        initialize(attrs)
    }

    fun getRecycler() = binding.recyclerView

    fun getAll() = binding.txtAll

    private fun initialize(attrs: AttributeSet) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomRecyclerHorizontal)
        val title = typeArray.getString(R.styleable.CustomRecyclerHorizontal_title)
        val desc = typeArray.getString(R.styleable.CustomRecyclerHorizontal_desc)

        if (desc.isNullOrEmpty())
            binding.txtDesc.visibility = View.GONE
        else
            binding.txtDesc.text = desc

        val allGroup = typeArray.getBoolean(R.styleable.CustomRecyclerHorizontal_all, false)
        val recyclerWhiteBackResId =
            typeArray.getResourceId(R.styleable.CustomRecyclerHorizontal_recycler_background, 0)
        val recyclerPaddingBottom = typeArray.getDimensionPixelSize(
            R.styleable.CustomRecyclerHorizontal_recycler_padding_bottom,
            0
        )

        binding.recyclerView.setBackgroundResource(recyclerWhiteBackResId)
        binding.recyclerView.setPadding(
            0,
            0,
            0,
            recyclerPaddingBottom
        )

        if (allGroup)
            binding.allGroup.visibility = View.INVISIBLE

        binding.txtTitle.text = title
        typeArray.recycle()
    }
}