package ir.abyx.pastry.mvp.ext

import android.content.res.Resources
import android.util.TypedValue

object OthersUtilities {

    fun splitPrice(price: Int) = "%,d".format(price)

    fun getPixel(dip: Float, resources: Resources): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip,
            resources.displayMetrics
        ).toInt()
    }

}