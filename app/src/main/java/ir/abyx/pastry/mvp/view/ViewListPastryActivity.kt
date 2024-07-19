package ir.abyx.pastry.mvp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.abyx.pastry.adapter.recycler.ProductListRecyclerAdapter
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.data.remote.dataModel.AllPastriesModel
import ir.abyx.pastry.data.remote.dataModel.ListPastriesModel
import ir.abyx.pastry.databinding.ActivityListPastryBinding

class ViewListPastryActivity(
    private val context: Context,
    private val activityUtils: ActivityUtils
) {

    val binding = ActivityListPastryBinding.inflate(LayoutInflater.from(context))

    fun initialize(pastries: ListPastriesModel) {

        val myAdapter = ProductListRecyclerAdapter(context, pastries.category.pastries)

        binding.apply {
            recyclerViewPastry.apply {
                layoutManager =
                    LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = myAdapter
            }

            txtTitle.text = pastries.category.title
            edtSearch.getEditText().doOnTextChanged { text, _, _, _ ->
                myAdapter.filter.filter(text)
            }
        }
    }

    fun initialize2(allPastries: AllPastriesModel, title: String) {

        val myAdapter = ProductListRecyclerAdapter(context, allPastries.pastries)

        binding.apply {

            recyclerViewPastry.apply {
                layoutManager =
                    LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = myAdapter
            }

            edtSearch.getEditText().doOnTextChanged { text, _, _, _ ->
                myAdapter.filter.filter(text)
            }

            txtTitle.text = title

        }
    }


    fun endProgress() {
        binding.apply {
            progressBar.visibility = View.GONE
            allViews.visibility = View.VISIBLE
        }
    }

    fun backButton() {
        binding.customAppBar.getBackIcon().setOnClickListener {
            activityUtils.finished()
        }
    }

}