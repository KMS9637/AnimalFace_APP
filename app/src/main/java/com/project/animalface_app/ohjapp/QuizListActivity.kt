package com.project.animalface_app.ohjapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.project.animalface_app.R
import kotlinx.coroutines.launch

class QuizListActivity : AppCompatActivity() {
    private lateinit var quizService: QuizService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_list) // XML 이름 일치

        // Retrofit 초기화 및 퀴즈 목록 불러오기
        quizService = RetrofitInstance.api  // getRetrofitInstance() 대신 RetrofitInstance.api 사용
        loadQuizList()
    }

    private fun loadQuizList() {
        lifecycleScope.launch {
            val response = quizService.getQuizList()
            if (response.isSuccessful) {
                val quizList = response.body()
                // RecyclerView에 퀴즈 목록 표시
                setupRecyclerView(quizList)
            }
        }
    }

    private fun setupRecyclerView(quizList: List<Quiz>?) {
        // RecyclerView 어댑터 설정
    }
}