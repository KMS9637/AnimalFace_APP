package com.project.animalface_app.kdkapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.project.animalface_app.R

class AnimalFaceResultActivity : AppCompatActivity() {

    private lateinit var resultImageView: ImageView
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_face_result)

        // 레이아웃의 ImageView와 TextView 바인딩
        resultImageView = findViewById(R.id.cameraImg)
        resultTextView = findViewById(R.id.result_text)

        // 인텐트로부터 받은 데이터를 처리
        val predictedClassLabel = intent.getStringExtra("predictedClassLabel")
        val confidence = intent.getDoubleExtra("confidence", 0.0)

        // 결과 텍스트를 설정
        resultTextView.text = "결과: $predictedClassLabel\n정확도: ${formatToPercentage(confidence)}"

        // 결과값에 따른 로컬 이미지 설정
        val resultImageResource = getImageResource(predictedClassLabel)
        resultImageView.setImageResource(resultImageResource)
    }

    // 예측 결과에 따라 로컬 이미지 리소스를 선택하는 함수
    private fun getImageResource(predictedClassLabel: String?): Int {
        return when (predictedClassLabel) {
            "강아지상" -> R.drawable.dog_face_image // 강아지상 이미지
            "고양이상" -> R.drawable.cat_face_image  // 고양이상 이미지
            "토끼상" -> R.drawable.rabbit_face_image // 토끼상 이미지
            else -> R.drawable.default_face_image   // 기본 이미지
        }
    }

    // 정확도를 퍼센트로 변환하는 함수
    private fun formatToPercentage(value: Double): String {
        return String.format("%.2f%%", value * 100)
    }
}