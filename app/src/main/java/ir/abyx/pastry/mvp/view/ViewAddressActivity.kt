package ir.abyx.pastry.mvp.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.abyx.pastry.adapter.recycler.AddressRecyclerAdapter
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.data.remote.dataModel.Address
import ir.abyx.pastry.databinding.ActivityAddressBinding
import ir.abyx.pastry.mvp.ext.ViewUtils
import ir.abyx.pastry.ui.activity.EditAddressActivity

class ViewAddressActivity(
    private val context: Context,
    private val activityUtils: ActivityUtils
) {

    val binding = ActivityAddressBinding.inflate(LayoutInflater.from(context))
    private lateinit var myAdapter: AddressRecyclerAdapter

    fun initialize(data: Address, viewUtils: ViewUtils) {
        myAdapter = AddressRecyclerAdapter(context, data.addresses, viewUtils)

        binding.recyclerAddress.getRecycler().apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = myAdapter
        }

        if (data.addresses.isNotEmpty())
            binding.txtNoAddress.visibility = View.GONE

        binding.btnAddAddress.getView().setOnClickListener {
            context.startActivity(Intent(context, EditAddressActivity::class.java))
        }
    }

    fun updateRecycler(data: Address) {
        myAdapter.dataUpdate(data.addresses)
    }

    fun endProgress() {
        binding.apply {
            progressBar.visibility = View.GONE
            viewGroup.visibility = View.VISIBLE
        }
    }

    fun backButton() {
        binding.customAppBar.getBackIcon().setOnClickListener {
            activityUtils.finished()
        }
    }
}