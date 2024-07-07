package ir.abyx.pastry.mvp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.abyx.pastry.adapter.recycler.PastriesHorizontalAdapter
import ir.abyx.pastry.adapter.recycler.PopularPastryRecyclerAdapter
import ir.abyx.pastry.adapter.recycler.SpecialOfferRecyclerAdapter
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.androidWrapper.PicassoHandler
import ir.abyx.pastry.data.remote.dataModel.PastriesModel
import ir.abyx.pastry.data.remote.dataModel.RequestMain
import ir.abyx.pastry.databinding.FragmentHomeBinding

class ViewHomeFragment(
    private val context: Context,
    private val activityUtils: ActivityUtils
) {

    val binding = FragmentHomeBinding.inflate(LayoutInflater.from(context))

    fun initialized(data: RequestMain) {
        val viewPager = binding.sliderViewPager
        viewPager.layoutDirection = View.LAYOUT_DIRECTION_RTL
        activityUtils.viewPagerFragment(viewPager, data.sliders)

        binding.customRecycler.getRecycler().layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, true)
        binding.customRecycler.getRecycler().adapter =
            PastriesHorizontalAdapter(context, data.pastries[0].pastries)

        binding.specialOfferRecycler.getRecycler().layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, true)

        val specialOffer = data.pastries[1].pastries
        specialOffer.add(
            0,
            PastriesModel(
                0,
                "",
                0,
                "",
                0,
                0,
                false,
                ""
            )
        )

        specialOffer.add(
            PastriesModel(
                0,
                "",
                0,
                "",
                0,
                0,
                false,
                ""
            )
        )

        binding.specialOfferRecycler.getRecycler().adapter =
            SpecialOfferRecyclerAdapter(context, specialOffer)

        val gridLayout = object : GridLayoutManager(context, 2) {
            override fun isLayoutRTL(): Boolean {
                return true
            }
        }

        binding.customRecyclerPopular.getRecycler().layoutManager =
            gridLayout


        binding.customRecyclerPopular.getRecycler().adapter =
            PopularPastryRecyclerAdapter(context, data.pastries[2].pastries)

        if (data.banners.isNotEmpty() && data.banners[0].large.isNotEmpty())
            PicassoHandler.setPicassoBanner(binding.imgBanner, data.banners[0].large)

    }

    fun endProgress() {
        binding.progressBar.visibility = View.GONE
        binding.contentLayout.visibility = View.VISIBLE
    }

}