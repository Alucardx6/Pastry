package ir.abyx.pastry.ui.activity

import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ir.abyx.pastry.androidWrapper.DeviceInfo
import ir.abyx.pastry.data.remote.dataModel.DefaultModel
import ir.abyx.pastry.data.remote.ext.ErrorUtils
import ir.abyx.pastry.data.remote.mainService.RetrofitService
import ir.abyx.pastry.databinding.ActivityRequestCustomCakeBinding
import ir.abyx.pastry.mvp.ext.ToastUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.InputStream

class RequestCustomCakeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRequestCustomCakeBinding
    private lateinit var baseUri: Uri
    private var uriState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRequestCustomCakeBinding.inflate(LayoutInflater.from(this))

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            customAppBar.getBackIcon().setOnClickListener {
                finish()
            }

            viewAddImage.setOnClickListener {
                pickImageLauncher.launch("image/*")
            }

            viewPlus.setOnClickListener {
                var count = txtCount.text.toString().toInt()

                if (count < 5)
                    count++

                txtCount.text = count.toString()
            }

            viewMin.setOnClickListener {
                var count = txtCount.text.toString().toInt()

                if (count > 1)
                    count--

                txtCount.text = count.toString()
            }

            viewPlus2.setOnClickListener {
                var count = txtCountRate.text.toString().toInt()

                if (count < 3)
                    count++

                txtCountRate.text = count.toString()
            }

            viewMin2.setOnClickListener {
                var count = txtCountRate.text.toString().toInt()

                if (count > 1)
                    count--

                txtCountRate.text = count.toString()
            }

            btnConfirm.getView().setOnClickListener {
                btnConfirm.enableProgress()
                val text = edtComment.text.toString()
                val weight = txtCount.text.toString()
                val floor = txtCountRate.text.toString()

                if (text.isNotEmpty() && text.length > 10) {
                    if (uriState) {
                        val files = listOf(uriToFile(baseUri)!!).map { file ->
                            MultipartBody.Part.createFormData(
                                "file",
                                file.name,
                                RequestBody.create(MediaType.parse("*/*"), file)
                            )
                        }

                        val service = RetrofitService.customCakeApiService
                        var message: String

                        CoroutineScope(Dispatchers.IO).launch {
                            val response = service.sendCakes(
                                DeviceInfo.getApi(this@RequestCustomCakeActivity),
                                DeviceInfo.getDeviceID(this@RequestCustomCakeActivity),
                                DeviceInfo.getPublicKey(this@RequestCustomCakeActivity),
                                files,
                                RequestBody.create(MediaType.parse("text/plain"), text),
                                RequestBody.create(MediaType.parse("text/plain"), weight),
                                RequestBody.create(MediaType.parse("text/plain"), floor)
                            )

                            if (response.isSuccessful) {
                                val result = response.body() as DefaultModel
                                message = result.message
                            } else
                                message = ErrorUtils.getError(response)

                            launch(Dispatchers.Main) {
                                ToastUtils.toast(this@RequestCustomCakeActivity, message)
                                btnConfirm.disableProgress()
                            }
                        }

                    } else {
                        ToastUtils.toast(this@RequestCustomCakeActivity, "لطفا تصویر را وارد کنید")
                        btnConfirm.disableProgress()
                    }

                } else {
                    ToastUtils.toast(this@RequestCustomCakeActivity, "لطفا توضیحات کاملی وارد کنید")
                    btnConfirm.disableProgress()
                }
            }
        }
    }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                if (checkImageSize(uri, contentResolver)) {
                    uriState = true
                    baseUri = uri
                    binding.imgCake.setImageURI(uri)
                }
            }
        }

    private fun checkImageSize(imageUri: Uri, contentResolver: ContentResolver): Boolean {

        try {
            val inputStream = contentResolver.openInputStream(imageUri)
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream?.close()

            val imageWidth = options.outWidth
            val imageHeight = options.outHeight
            val imageSize = imageWidth * imageHeight // حجم تقریبی تصویر (بدون فشرده‌سازی)

            if (imageSize > 9_000_000) {
                ToastUtils.toast(this, "از تصاویر با سایز کوچک تر استفاده کنید")
                return false
            }

            return true

        } catch (e: Exception) {
            Log.e("CheckImageSize", "Error occurred while checking image size: ${e.message}")
            return false
        }

    }

    private fun uriToFile(uri: Uri): File? {

        val inputStream: InputStream? = contentResolver.openInputStream(uri)

        val fileExtension: String? = when (contentResolver.getType(uri)) {
            "image/jpeg" -> "jpg"
            "image/png" -> "png"
            else -> null // پسوند معتبر برای تصویر مشخص نشده است
        }

        if (fileExtension != null) {
            val tempFile = File.createTempFile("image", ".$fileExtension")
            inputStream?.copyTo(tempFile.outputStream())
            return tempFile
        }

        inputStream?.close()

        return null

    }

}