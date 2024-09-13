// QuizService.kt
package com.project.animalface_app.ohjapp

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Quiz 데이터 클래스
data class Quiz(
    val quiz_no: Long,
    val name: String,
    val category: String,
    val imageUrl: String
)

// QuizQuestion 데이터 클래스
data class QuizQuestion(
    val question_no: Long,
    val question_text: String,
    val quiz_no: Long
)

// AnswerRequest 데이터 클래스 (정답 제출 시 사용)
data class AnswerRequest(
    val question_no: Long,
    val user_answer: String
)

// AnswerResult 데이터 클래스 (정답 여부 확인)
data class AnswerResult(
    val isCorrect: Boolean,
    val correct_answer: String
)

// Retrofit API 정의 (QuizService 인터페이스)
interface QuizService {

    // 퀴즈 목록 가져오기
    @GET("/quiz/list")
    suspend fun getQuizList(): Response<List<Quiz>>

    // 특정 퀴즈의 문제 가져오기
    @GET("/quiz/{quiz_no}/start/{quiz_question_no}")
    suspend fun getQuestion(
        @Path("quiz_no") quizNo: Long,
        @Path("quiz_question_no") questionNo: Long
    ): Response<QuizQuestion>

    // 사용자가 입력한 답 제출하기
    @POST("/quiz/{quiz_no}/answer")
    suspend fun submitAnswer(
        @Path("quiz_no") quizNo: Long,
        @Body answerRequest: AnswerRequest
    ): Response<AnswerResult>
}
