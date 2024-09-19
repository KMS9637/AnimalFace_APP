package com.project.animalface_app.ohjapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.animalface_app.R
import kotlinx.coroutines.launch

class QuizListActivity : AppCompatActivity() {
    private lateinit var quizService: QuizService
    private lateinit var quizRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_list)

        quizRecyclerView = findViewById(R.id.quizRecyclerView)
        quizRecyclerView.layoutManager = LinearLayoutManager(this)

        quizService = RetrofitInstance.api
        loadQuizList()
    }

    private fun loadQuizList() {
        lifecycleScope.launch {
            try {
                val response = quizService.getQuizList()
                if (response.isSuccessful) {
                    val quizList = response.body()
                    Log.d("QuizListActivity", "Received quiz list: $quizList")  // 응답 데이터 확인
                    if (quizList != null && quizList.isNotEmpty()) {
                        setupRecyclerView(quizList)
                    } else {
                        Log.e("QuizListActivity", "Quiz list is null or empty")
                    }
                } else {
                    Log.e("QuizListActivity", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("QuizListActivity", "Error: ${e.message}")
            }
        }
    }



    private fun setupRecyclerView(quizList: List<Quiz>) {
        val adapter = QuizListAdapter(quizList) { quiz ->
            val intent = Intent(this, QuizQuestionActivity::class.java)
            intent.putExtra("QUIZ_NO", quiz.quiz_no)
            startActivity(intent)
        }
        quizRecyclerView.adapter = adapter  // 어댑터를 RecyclerView에 연결
    }
}
