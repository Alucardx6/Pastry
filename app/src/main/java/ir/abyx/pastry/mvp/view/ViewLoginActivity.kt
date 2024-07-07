package ir.abyx.pastry.mvp.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import ir.abyx.pastry.R
import ir.abyx.pastry.androidWrapper.DeviceInfo
import ir.abyx.pastry.data.local.preferences.SecureSharePref
import ir.abyx.pastry.data.local.preferences.SharedPrefKey
import ir.abyx.pastry.data.remote.dataModel.RequestSendPhone
import ir.abyx.pastry.data.remote.dataModel.RequestVerifyCode
import ir.abyx.pastry.data.remote.ext.ErrorUtils
import ir.abyx.pastry.data.remote.mainService.RetrofitService
import ir.abyx.pastry.databinding.ActivityLoginBinding
import ir.abyx.pastry.databinding.CustomDialogLoginBinding
import ir.abyx.pastry.databinding.CustomDialogUsernameBinding
import ir.abyx.pastry.ui.activity.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.concurrent.TimeUnit


class ViewLoginActivity(private val context: Context) {

    private val service = RetrofitService.loginApiService
    private val shared = SecureSharePref.getSharedPref(context)
    private lateinit var deviceInfo: DeviceInfo

    val binding =
        ActivityLoginBinding.inflate(LayoutInflater.from(context))

    fun setDeviceInfo(info: DeviceInfo) {
        deviceInfo = info
    }

    fun onClick(uid: String, publicKey: String) {
        binding.btnLogin.getView().setOnClickListener {
            val phone = binding.edtLogin.getText()

            if (phone.trim().isNotEmpty()) {
                binding.edtLogin.setError(null)
                validateDialog(phone, uid, publicKey)
                Log.i("PUBLIC_KEY", publicKey)
            } else {
                binding.edtLogin.setError("لطفا شماره تماس خود را وارد کنید")
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun validateDialog(phone: String, uid: String, publicKey: String) {
        val view = CustomDialogLoginBinding.inflate(LayoutInflater.from(context))

        view.txtShowNumber.text = "کد ارسالی به شماره $phone را وارد کنید"

        val dialog = Dialog(context)
        dialog.setContentView(view.root)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        createTimer(view)
        sendCode(phone, uid, publicKey)

        view.icCancel.setOnClickListener { dialog.dismiss() }
        view.txtEditPhone.setOnClickListener { dialog.dismiss() }

        view.txtResend.setOnClickListener {
            createTimer(view)
            sendCode(phone, uid, publicKey)
        }

        view.customButton.getView()
            .setOnClickListener {
                val code = view.edtCode.text.toString().trim()
                val inputLayout = view.codeInputLayout
                if (code.isNotEmpty()) {
                    inputLayout.error = null
                    view.loadingGroup.visibility = View.VISIBLE
                    changeStatus(view, false)

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val response = service.verifyCode(phone, code)
                            if (response.isSuccessful) {
                                launch(Dispatchers.Main) {
                                    val data = response.body() as RequestVerifyCode
                                    Toast.makeText(
                                        context, "message: ${
                                            data
                                                .message
                                        }\n${data.api}", Toast.LENGTH_SHORT
                                    ).show()

                                    val edit =
                                        shared.edit().putString(SharedPrefKey.API_KEY, data.api)
                                    edit.apply()

                                    dialog.dismiss()
                                    usernameDialog()
                                }

                            } else {
                                launch(Dispatchers.Main) {
                                    Toast.makeText(
                                        context,
                                        ErrorUtils.getError(response),
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()

                                    view.loadingGroup.visibility = View.GONE
                                    changeStatus(view, true)
                                    inputLayout.error = "کد وارد شده اشتباه است"
                                }
                            }
                        } catch (e: Exception) {
                            Log.i("ERROR_SERVER", e.message.toString())
                        }
                    }

                } else {
                    inputLayout.error = "لطفا کد را وارد کنید"
                }
            }
    }

    private fun sendCode(phone: String, uid: String, publicKey: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.sendPhone(uid, publicKey, phone)

                if (response.isSuccessful) {
                    launch(Dispatchers.Main) {
                        val data = response.body() as RequestSendPhone
                        Toast.makeText(context, data.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    launch(Dispatchers.Main) {
                        Toast.makeText(context, ErrorUtils.getError(response), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (e: Exception) {
                Log.i("ERROR_SERVER", e.message.toString())
            }

        }
    }

    private fun usernameDialog() {
        val view = CustomDialogUsernameBinding.inflate(LayoutInflater.from(context))
        val dialog = Dialog(context)
        dialog.setContentView(view.root)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        view.icCancel.setOnClickListener { dialog.dismiss() }

        view.customButton.getView().setOnClickListener {

            view.usernameInputLayout.error = null
            view.customButton.enableProgress()

            val name = view.edtUsername.text.toString()
            if (name.trim().isNotEmpty() || name.length < 3) {
                CoroutineScope(Dispatchers.IO).launch {
                    val response = service.editUser(
                        DeviceInfo.getApi(context),
                        DeviceInfo.getDeviceID(context),
                        DeviceInfo.getPublicKey(context),
                        name
                    )
                    if (response.isSuccessful) {
                        CoroutineScope(Dispatchers.Main).launch {
                            view.customButton.disableProgress()
                            val editLogin = shared.edit()
                            editLogin.putBoolean(
                                SharedPrefKey.LOGIN_STATE_KEY,
                                true
                            )
                            editLogin.apply()

                            context.startActivity(Intent(context, MainActivity::class.java))
                        }
                    } else {
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(
                                context,
                                ErrorUtils.getError(response),
                                Toast.LENGTH_SHORT
                            ).show()

                            view.customButton.disableProgress()
                        }
                    }
                }
            } else
                view.usernameInputLayout.error = "لطفا نام کاربری خود را وارد کنید"
        }
    }

    private fun changeStatus(view: CustomDialogLoginBinding, state: Boolean) {
        view.icCancel.isEnabled = state
        view.txtEditPhone.isEnabled = state
        view.txtShowNumber.isEnabled = state
        view.customButton.getView().isEnabled = state
        view.edtCode.isEnabled = state

        view.icCancel.isClickable = state
        view.txtEditPhone.isClickable = state
        view.txtShowNumber.isClickable = state
        view.customButton.getView().isClickable = state
        view.edtCode.isClickable = state
    }

    private fun createTimer(view: CustomDialogLoginBinding) {
        view.txtResend.setTextColor(ContextCompat.getColor(context, R.color.color_text_gray))
        view.txtResend.isEnabled = false

        object : CountDownTimer(180000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(p0: Long) {
                val text = java.lang.String.format(
                    Locale.getDefault(), "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(p0) % 60,
                    TimeUnit.MILLISECONDS.toSeconds(p0) % 60
                )
                view.edtCodeTimer.text = text
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                view.edtCodeTimer.text = "00:00"
                view.txtResend.setTextColor(ContextCompat.getColor(context, R.color.black_main))
                view.txtResend.isEnabled = true
            }

        }.start()
    }
}