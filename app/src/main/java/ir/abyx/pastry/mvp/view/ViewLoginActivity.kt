package ir.abyx.pastry.mvp.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import ir.abyx.pastry.R
import ir.abyx.pastry.databinding.ActivityLoginBinding
import ir.abyx.pastry.databinding.CustomDialogLoginBinding
import ir.abyx.pastry.databinding.CustomDialogUsernameBinding
import ir.abyx.pastry.ui.activity.MainActivity

class ViewLoginActivity(private val context: Context) {

    val binding =
        ActivityLoginBinding.inflate(LayoutInflater.from(context))

    fun onClick() {
        binding.btnLogin.getView().setOnClickListener {
            val phone = binding.edtLogin.getText()

            if (phone.trim().isNotEmpty()) {
                binding.edtLogin.setError(null)
                validateDialog(phone)
            } else {
                binding.edtLogin.setError("لطفا شماره تماس خود را وارد کنید")
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun validateDialog(phone: String) {
        val view = CustomDialogLoginBinding.inflate(LayoutInflater.from(context))

        view.txtShowNumber.text = "کد ارسالی به شماره $phone را وارد کنید"

        val dialog = Dialog(context)
        dialog.setContentView(view.root)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        createTimer(view)

        Toast.makeText(context, "کد ارسال شد", Toast.LENGTH_SHORT).show()

        view.icCancel.setOnClickListener { dialog.dismiss() }
        view.txtEditPhone.setOnClickListener { dialog.dismiss() }

        view.txtResend.setOnClickListener {
            createTimer(view)
        }

        view.customButton.getView()
            .setOnClickListener {
                val code = view.edtCode.text.toString().trim()
                val inputLayout = view.codeInputLayout
                if (code.isNotEmpty()) {
                    inputLayout.error = null
                    view.loadingGroup.visibility = View.VISIBLE
                    changeStatus(view, false)

                    Handler(Looper.getMainLooper()).postDelayed({
                        if (validateCode(code)) {
                            inputLayout.error = null
                            dialog.dismiss()
                            usernameDialog()
                        } else {
                            view.loadingGroup.visibility = View.GONE
                            changeStatus(view, true)
                            inputLayout.error = "کد وارد شده اشتباه است"
                        }
                    }, 3000)

                } else {
                    inputLayout.error = "لطفا کد را وارد کنید"
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
            if (view.edtUsername.text.toString().trim().isNotEmpty()) {
                view.usernameInputLayout.error = null
                context.startActivity(Intent(context, MainActivity::class.java))
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

        object : CountDownTimer(70000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(p0: Long) {
                view.edtCodeTimer.text = "00:${p0 / 1000}"
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                view.edtCodeTimer.text = "00:00"
                view.txtResend.setTextColor(ContextCompat.getColor(context, R.color.black_main))
                view.txtResend.isEnabled = true
            }

        }.start()
    }

    private fun validateCode(code: String): Boolean {
        return code == "1234"
    }
}