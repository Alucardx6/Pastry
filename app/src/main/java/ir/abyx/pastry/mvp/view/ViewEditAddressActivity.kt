package ir.abyx.pastry.mvp.view

import android.content.Context
import android.view.LayoutInflater
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.databinding.ActivityEditAddressBinding
import ir.abyx.pastry.mvp.ext.ViewUtils

class ViewEditAddressActivity(
    context: Context,
    private val id: Int,
    private val state: Boolean,
    private val receiver: String,
    private val phone: String,
    private val address: String,
    private val activityUtils: ActivityUtils
) {

    val binding = ActivityEditAddressBinding.inflate(LayoutInflater.from(context))

    fun initialize(viewUtils: ViewUtils) {

        val edtName = binding.edtName
        val edtPhone = binding.edtPhone
        val edtAddress = binding.edtAddress

        if (state) {
            edtName.setText(receiver)
            edtPhone.setText(phone)
            edtAddress.setText(address)
        }

        binding.btnSave.getView().setOnClickListener {
            binding.btnSave.enableProgress()

            if (state)
                viewUtils.editAddress(
                    edtName.getText(),
                    edtPhone.getText(),
                    edtAddress.getText(),
                    id.toString()
                )
            else
                viewUtils.setAddress(edtName.getText(), edtPhone.getText(), edtAddress.getText())
        }
    }

    fun backButton() {
        binding.customAppBar.getBackIcon().setOnClickListener {
            activityUtils.finished()
        }
    }

    fun disableProgress() {
        binding.btnSave.disableProgress()
    }

    fun finish() {
        activityUtils.finished()
    }

}