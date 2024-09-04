//package com.project.animalface_app.kdkapp
//
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.ImageButton
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.project.animalface_app.R
//
//class CreateGameActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_create_game)
//
//        val backButton = findViewById<ImageButton>(R.id.backButton)
//        val submitButton = findViewById<Button>(R.id.submitButton)
//        val titleInput = findViewById<EditText>(R.id.titleInput)
//        val questionInput = findViewById<EditText>(R.id.questionInput)
//        val answerInput = findViewById<EditText>(R.id.answerInput)
//        val resultInput = findViewById<EditText>(R.id.resultInput)
//
//        backButton.setOnClickListener {
//            // 뒤로 가기 버튼 클릭 시 액티비티 종료
//            finish()
//        }
//
//        submitButton.setOnClickListener {
//            // 입력된 데이터를 가져와서 처리
//            val title = titleInput.text.toString()
//            val question = questionInput.text.toString()
//            val answer = answerInput.text.toString()
//            val result = resultInput.text.toString()
//
//            if (title.isEmpty() || question.isEmpty() || answer.isEmpty() || result.isEmpty()) {
//                Toast.makeText(this, "모든 필드를 채워 주세요.", Toast.LENGTH_SHORT).show()
//            } else {
//                // 데이터를 전송하거나 저장하는 코드 추가
//                Toast.makeText(this, "데이터가 성공적으로 전송되었습니다.", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//}
