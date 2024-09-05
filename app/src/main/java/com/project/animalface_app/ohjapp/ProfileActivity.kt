package com.project.animalface_app.ohjapp

import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.animalface_app.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        // Edge-to-Edge 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 닉네임 수정 기능 구현
        findViewById<ImageButton>(R.id.btn_edit_nickname).setOnClickListener {
            val editText = EditText(this)
            AlertDialog.Builder(this)
                .setTitle("닉네임 수정")
                .setView(editText)
                .setPositiveButton("저장") { dialog, which ->
                    val newNickname = editText.text.toString()
                    // 닉네임 저장 로직 (예: 서버 업데이트, 로컬 저장 등)
                    findViewById<TextView>(R.id.tv_nickname).text = newNickname
                }
                .setNegativeButton("취소", null)
                .show()
        }

        // 소개 키워드 수정 기능 구현
        findViewById<ImageButton>(R.id.btn_edit_intro_keyword).setOnClickListener {
            val editText = EditText(this)
            AlertDialog.Builder(this)
                .setTitle("소개 키워드 수정1")
                .setView(editText)
                .setPositiveButton("저장") { dialog, which ->
                    val newKeyword = editText.text.toString()
                    // 키워드 저장 로직 (예: 서버 업데이트, 로컬 저장 등)
                    findViewById<TextView>(R.id.tv_intro_keyword).text = newKeyword
                }
                .setNegativeButton("취소", null)
                .show()
        }

        // 삭제 기능 구현
        findViewById<ImageButton>(R.id.btn_delete_account).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("계정 삭제")
                .setMessage("정말로 계정을 삭제하시겠습니까?")
                .setPositiveButton("삭제") { dialog, which ->
                    // 계정 삭제 로직 (예: 서버 호출, 데이터베이스 삭제 등)
                }
                .setNegativeButton("취소", null)
                .show()
        }
    }
}
