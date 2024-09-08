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
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.project.animalface_app.R
import com.project.animalface_app.databinding.ActivityAnimalFaceBinding
import com.project.animalface_app.kdkapp.dto.PredictionResult
import com.project.animalface_app.kdkapp.network.INetworkService
import com.project.animalface_app.kdkapp.network.MyApplication
import kotlinx.coroutines.Dispatchers
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

        // API 서비스 초기화
        apiService = MyApplication.getApiService()

        // 갤러리에서 이미지 선택
        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val dataUri = result.data?.data
            if (dataUri != null) {
                imageUri = dataUri
                imageView.setImageURI(dataUri)
                Log.d("AnimalFaceActivity", "갤러리에서 선택된 이미지 URI: $imageUri")
            } else {
                Log.e("AnimalFaceActivity", "갤러리에서 이미지를 선택하지 않았습니다.")
            }
        }

        binding.galleryBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            requestGalleryLauncher.launch(intent)
            Log.d("AnimalFaceActivity", "갤러리 버튼 클릭됨")
        }

        // 카메라로 이미지 캡처
        val requestCameraFileLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val bitmap = BitmapFactory.decodeFile(filePath)
                imageView.setImageBitmap(bitmap)
                imageUri = Uri.fromFile(File(filePath))
                Log.d("AnimalFaceActivity", "카메라로 촬영된 이미지 URI: $imageUri")
            } else {
                Toast.makeText(this, "사진을 촬영하지 않았습니다.", Toast.LENGTH_SHORT).show()
                Log.e("AnimalFaceActivity", "카메라로 사진 촬영 실패")
            }
        }

        binding.cameraBtn.setOnClickListener {
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)

            filePath = file.absolutePath

            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "${applicationContext.packageName}.fileprovider",
                file
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            requestCameraFileLauncher.launch(intent)
            Log.d("AnimalFaceActivity", "카메라 버튼 클릭됨")
        }

        // "테스트 실행" 버튼 클릭 시 서버로 이미지 전송 및 결과 액티비티로 이동
        binding.predictSendBtn.setOnClickListener {
            if (::imageUri.isInitialized) {
                Log.d("AnimalFaceActivity", "테스트 실행 버튼 클릭됨, 이미지 URI: $imageUri")
                processImage(imageUri)
            } else {
                Toast.makeText(this, "이미지를 선택하세요.", Toast.LENGTH_SHORT).show()
                Log.e("AnimalFaceActivity", "이미지 URI가 초기화되지 않았습니다.")
            }
        }
    }

    // 이미지 처리 후 서버로 전송하는 함수
    private fun processImage(uri: Uri) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val bitmap = getResizedBitmap(uri, 200, 200) // 200x200 크기로 축소
                val imageBytes = bitmapToByteArray(bitmap)
                val profileImagePart = createMultipartBodyFromBytes(imageBytes)

                uploadData(profileImagePart)

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("AnimalFaceActivity", "이미지 처리 중 오류 발생: ${e.message}")
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
        val call = apiService.predictImage(profileImage)

        call.enqueue(object : Callback<PredictionResult> {
            override fun onResponse(call: Call<PredictionResult>, response: Response<PredictionResult>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        Log.d("AnimalFaceActivity", "서버 응답: ${it.predictedClassLabel}")
                        val intent = Intent(this@AnimalFaceActivity, AnimalFaceResultActivity::class.java)
                        intent.putExtra("predictedClassLabel", it.predictedClassLabel)
                        intent.putExtra("confidence", it.confidence)
                        startActivity(intent)
                    }
                } else {
                    Log.e("AnimalFaceActivity", "서버 응답 실패: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<PredictionResult>, t: Throwable) {
                Toast.makeText(this@AnimalFaceActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("AnimalFaceActivity", "네트워크 오류: ${t.message}")
            }
        })
    }
}
