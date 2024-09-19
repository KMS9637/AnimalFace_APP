package com.project.animalface_app.ohjapp

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface QuizService {

    @GET("/quiz/list")
    suspend fun getQuizList(): Response<List<Quiz>>

    @GET("/quiz/{quiz_no}/start/{quiz_question_no}")
    suspend fun getQuestion(
        @Path("quiz_no") quizNo: Long,
        @Path("quiz_question_no") questionNo: Long
    ): Response<QuizQuestion>

    @POST("/quiz/{quiz_no}/answer")
    suspend fun submitAnswer(
        @Path("quiz_no") quizNo: Long,
        @Body answerRequest: AnswerRequest
    ): Response<AnswerResult>
}
