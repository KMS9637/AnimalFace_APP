package com.project.animalface_app.ohjapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.project.animalface_app.R

class QuizResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        val totalCorrect = intent.getIntExtra("TOTAL_CORRECT", 0)
        val resultTextView: TextView = findViewById(R.id.resultTextView)
        resultTextView.text = "총 $totalCorrect 문제를 맞추셨습니다."
    }
}
