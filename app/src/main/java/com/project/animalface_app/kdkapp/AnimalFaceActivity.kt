package com.project.animalface_app.kdkapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.appliances.recycle.network.MyApplication
import com.project.animalface_app.R
import com.project.animalface_app.databinding.ActivityAnimalFaceBinding
import com.project.animalface_app.kdkapp.dto.PredictionResult
import com.project.animalface_app.kdkapp.network.INetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class AnimalFaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimalFaceBinding
    private lateinit var filePath: String
    private lateinit var imageUri: Uri
    private lateinit var imageView: ImageView
    private lateinit var apiService: INetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimalFaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ImageView를 바인딩
        imageView = binding.resultUserImage

        // 갤러리에서 이미지 선택
        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val dataUri = result.data?.data
            if (dataUri != null) {
                imageUri = dataUri
                imageView.setImageURI(dataUri) // 선택된 이미지를 ImageView에 표시
            }
        }

        binding.galleryBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            requestGalleryLauncher.launch(intent)
        }

        // 카메라로 이미지 캡처
        val requestCameraFileLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val bitmap = BitmapFactory.decodeFile(filePath)
            imageView.setImageBitmap(bitmap)
            imageUri = Uri.fromFile(File(filePath)) // 촬영한 이미지의 URI 저장
        }

        binding.cameraBtn.setOnClickListener {
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)

            filePath = file.absolutePath

            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.project.animalface_app",
                file
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            requestCameraFileLauncher.launch(intent)
        }

        // "테스트 실행" 버튼 클릭 시 서버로 이미지 전송 및 결과 액티비티로 이동
        binding.predictSendBtn.setOnClickListener {
            if (::imageUri.isInitialized) {
                processImage(imageUri)
            } else {
                Toast.makeText(this, "이미지를 선택하세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 이미지 처리 후 서버로 전송하는 함수
    private fun processImage(uri: Uri) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val bitmap = getResizedBitmap(uri, 200, 200) // 200x200 크기로 축소
                val imageBytes = bitmapToByteArray(bitmap)
                val profileImagePart = createMultipartBodyFromBytes(imageBytes)

                uploadData(profileImagePart)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 이미지 크기 조정 및 비트맵 변환 함수
    private suspend fun getResizedBitmap(uri: Uri, width: Int, height: Int): Bitmap {
        return withContext(Dispatchers.IO) {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            Bitmap.createScaledBitmap(bitmap, width, height, false)
        }
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    // MultipartBody 생성
    private fun createMultipartBodyFromBytes(imageBytes: ByteArray): MultipartBody.Part {
        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), imageBytes)
        return MultipartBody.Part.createFormData("image", "image.jpg", requestFile)
    }

    // 서버로 이미지 전송 후 결과를 받는 함수
    private fun uploadData(profileImage: MultipartBody.Part) {
        val apiService = (applicationContext as MyApplication).getApiService()
        val call = apiService.predictImage(profileImage)

        call.enqueue(object : Callback<PredictionResult> {
            override fun onResponse(call: Call<PredictionResult>, response: Response<PredictionResult>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        val intent = Intent(this@AnimalFaceActivity, AnimalFaceResultActivity::class.java)
                        intent.putExtra("predictedClassLabel", it.predictedClassLabel)
                        intent.putExtra("confidence", it.confidence)
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(call: Call<PredictionResult>, t: Throwable) {
                Toast.makeText(this@AnimalFaceActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
