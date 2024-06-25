package ir.abyx.pastry.mvp.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import ir.abyx.pastry.R
import ir.abyx.pastry.databinding.ActivityLoginBinding
import ir.abyx.pastry.databinding.CustomDialogLoginBinding
import ir.abyx.pastry.ui.activity.MainActivity

class ViewLoginActivity(private val context: Context) {

    val binding =
        ActivityLoginBinding.inflate(LayoutInflater.from(context))

    fun onClick() {
        binding.btnLogin.getView().setOnClickListener {
            val phone = binding.edtLogin.getText()

            if (phone.trim().isNotEmpty()) {
                binding.edtLogin.setError(null)
                createDialog(phone)
            } else {
                binding.edtLogin.setError("لطفا شماره تماس خود را وارد کنید")
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun createDialog(phone: String) {
        val view = CustomDialogLoginBinding.inflate(LayoutInflater.from(context))

        view.txtShowNumber.text = "کد ارسالی به شماره $phone را وارد کنید"

        view.txtResend.setTextColor(ContextCompat.getColor(context, R.color.color_text_gray))
        view.txtResend.isEnabled = false

        val dialog = Dialog(context)
        dialog.setContentView(view.root)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        createTimer(view)

        Toast.makeText(context, "کد ارسال شد", Toast.LENGTH_SHORT).show()

        view.icCancel.setOnClickListener { dialog.cancel() }
        view.txtEditPhone.setOnClickListener { dialog.cancel() }

        view.customButton.getView().setOnClickListener { validateCode(view) }
    }

    private fun createTimer(view: CustomDialogLoginBinding) {
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

    private fun validateCode(view: CustomDialogLoginBinding) {
        if (view.edtCode.text.toString() == "1234")
            context.startActivity(Intent(context, MainActivity::class.java))
        else
            view.codeInputLayout.error = "کد وارد شده اشتباه است"
    }
}