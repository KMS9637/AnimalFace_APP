package com.project.animalface_app.ksyapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity

import com.project.animalface_app.R
import com.project.animalface_app.ohjapp.ksyAPI.CreateGameMainActivity


class Main2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        installSplashScreen()
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        val createGame: Button = findViewById(R.id.createGame)
        createGame.setOnClickListener {
            val intent = Intent(this, CreateGameMainActivity::class.java)
            startActivity(intent)
        }
    }
}
