package com.project.animalface_app.ohjapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.project.animalface_app.R
import kotlinx.coroutines.launch

class QuizQuestionActivity : AppCompatActivity() {
    private lateinit var quizService: QuizService
    private var currentQuestionNo: Long = 1L
    private var quizNo: Long = 1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        quizService = RetrofitInstance.api  // getRetrofitInstance() 대신 RetrofitInstance.api 사용

        quizNo = intent.getLongExtra("QUIZ_NO", 1L)
        currentQuestionNo = intent.getLongExtra("QUESTION_NO", 1L)

        loadQuestion()
    }

    private fun loadQuestion() {
        lifecycleScope.launch {
            val response = quizService.getQuestion(quizNo, currentQuestionNo)
            if (response.isSuccessful) {
                val question = response.body()
                displayQuestion(question)
            }
        }
    }

    private fun displayQuestion(question: QuizQuestion?) {
        // UI에 문제 텍스트와 입력 필드 표시
    }

    private fun submitAnswer(userAnswer: String) {
        lifecycleScope.launch {
            val answerRequest = AnswerRequest(currentQuestionNo, userAnswer)
            val response = quizService.submitAnswer(quizNo, answerRequest)
            if (response.isSuccessful) {
                val result = response.body()
                if (result?.isCorrect == true) {
                    moveToNextQuestion()
                } else {
                    // 정답 확인 및 다시 제출
                }
            }
        }
    }

    private fun moveToNextQuestion() {
        currentQuestionNo += 1
        loadQuestion()
    }
}