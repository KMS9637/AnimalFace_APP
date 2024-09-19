package com.project.animalface_app.ohjapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.project.animalface_app.R
import kotlinx.coroutines.launch

class QuizQuestionActivity : AppCompatActivity() {
    private lateinit var quizService: QuizService
    private lateinit var questionTextView: TextView
    private lateinit var answerEditText: EditText
    private lateinit var submitButton: Button

    private var currentQuestionNo: Long = 1L
    private var quizNo: Long = 1L
    private var totalCorrect: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        quizService = RetrofitInstance.api
        questionTextView = findViewById(R.id.questionTextView)
        answerEditText = findViewById(R.id.answerEditText)
        submitButton = findViewById(R.id.submitButton)

        quizNo = intent.getLongExtra("QUIZ_NO", 1L)

        loadQuestion()

        submitButton.setOnClickListener {
            val userAnswer = answerEditText.text.toString()
            submitAnswer(userAnswer)
        }
    }

    private fun loadQuestion() {
        lifecycleScope.launch {
            val response = quizService.getQuestion(quizNo, currentQuestionNo)
            if (response.isSuccessful) {
                val question = response.body()
                question?.let {
                    questionTextView.text = it.question_text
                }
            }
        }
    }

    private fun submitAnswer(userAnswer: String) {
        lifecycleScope.launch {
            val answerRequest = AnswerRequest(currentQuestionNo, userAnswer)
            val response = quizService.submitAnswer(quizNo, answerRequest)
            if (response.isSuccessful) {
                val result = response.body()
                if (result != null && result.isCorrect) {
                    totalCorrect++
                }
                moveToNextQuestion()
            }
        }
    }

    private fun moveToNextQuestion() {
        currentQuestionNo++
        loadQuestion()
    }
}
