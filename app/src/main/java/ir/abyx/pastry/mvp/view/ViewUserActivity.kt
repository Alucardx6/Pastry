package ir.abyx.pastry.mvp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import ir.abyx.pastry.R
import ir.abyx.pastry.androidWrapper.ActivityUtils
import ir.abyx.pastry.data.remote.dataModel.UserData
import ir.abyx.pastry.databinding.ActivityUserBinding
import ir.abyx.pastry.mvp.ext.ViewUtils

class ViewUserActivity(context: Context, private val activityUtils: ActivityUtils) {

    val binding = ActivityUserBinding.inflate(LayoutInflater.from(context))

    fun initialize(data: UserData, viewUtils: ViewUtils) {
        binding.txtName.text = data.fullname
        binding.txtPhone.text = data.phone

        binding.txtEditName.setText(data.fullname)
        binding.txtEditEmail.setText(data.email)

        if (!data.day.isNullOrEmpty())
            binding.txtEditDay.setText(data.day)
        if (!data.month.isNullOrEmpty())
            binding.txtEditMonth.setText(data.month)
        if (!data.year.isNullOrEmpty())
            binding.txtEditYear.setText(data.year)

        when (data.sex) {
            0 -> binding.rdbWomen.isChecked = true
            1 -> binding.rdbMen.isChecked = true
            else -> binding.rdbHide.isChecked = true
        }

        binding.btnSaveInfo.getView().setOnClickListener {
            binding.btnSaveInfo.enableProgress()

            val emptyError = "لطفا این فیلد را پر کنید"
            val name = binding.txtEditName.getText().trim()
            val email = binding.txtEditEmail.getText().trim()
            val day = binding.txtEditDay.getText().trim()
            val month = binding.txtEditMonth.getText().trim()
            val year = binding.txtEditYear.getText().trim()

            val gender =
                when (binding.rdg.checkedRadioButtonId) {
                    R.id.rdbMen -> 1
                    R.id.rdbWomen -> 0
                    else -> 2
                }

            if (name.isEmpty()) {
                binding.txtEditName.setError(emptyError)
                binding.btnSaveInfo.disableProgress()
                return@setOnClickListener
            } else
                binding.txtEditName.setError(null)

            if (email.isEmpty()) {
                binding.txtEditEmail.setError(emptyError)
                binding.btnSaveInfo.disableProgress()
                return@setOnClickListener
            } else
                binding.txtEditEmail.setError(null)

            viewUtils.setUserInfo(name, email, day, month, year, gender)
        }

    }

    fun backButton() {
        binding.customAppBar.getBackIcon().setOnClickListener {
            activityUtils.finished()
        }
    }

    fun endSaveInfo() {
        binding.btnSaveInfo.disableProgress()
    }

    fun endProgress() {
        binding.progressBar.visibility = View.GONE
        binding.mainView.visibility = View.VISIBLE
    }

}