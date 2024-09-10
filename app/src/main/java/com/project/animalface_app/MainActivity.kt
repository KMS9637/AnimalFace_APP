package com.project.animalface_app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import com.project.animalface_app.createGameAPI.CreateGameMainActivity


class MainActivity : AppCompatActivity() {

    private var isSidebarOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        val contentFrame: FrameLayout = findViewById(R.id.content)
        LayoutInflater.from(this).inflate(R.layout.activity_main2, contentFrame, true)

        val createGame: Button = findViewById(R.id.createGame)
        createGame.setOnClickListener {
            val intent = Intent(this, CreateGameMainActivity::class.java)
            startActivity(intent)
        }

        val searchButton = findViewById<ImageView>(R.id.searchButton)
        searchButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        val menuIcon: CheckBox = findViewById(R.id.menuicon)
        val sidebar: View = findViewById(R.id.sidebar)
        val menuIconLine1: View = findViewById(R.id.menuIconLine1)
        val menuIconLine2: View = findViewById(R.id.menuIconLine2)
        val menuIconLine3: View = findViewById(R.id.menuIconLine3)

        menuIcon.setOnCheckedChangeListener { _, isChecked ->
            toggleSidebar(isChecked, sidebar, menuIconLine1, menuIconLine2, menuIconLine3)
        }

        setupMenuClickListeners()

        setupHeaderClickListeners()
    }

    private fun toggleSidebar(
        isChecked: Boolean,
        sidebar: View,
        menuIconLine1: View,
        menuIconLine2: View,
        menuIconLine3: View
    ) {
        if (isChecked && !isSidebarOpen) {
            openSidebar(sidebar, menuIconLine1, menuIconLine2, menuIconLine3)
        } else if (!isChecked && isSidebarOpen) {
            closeSidebar(sidebar, menuIconLine1, menuIconLine2, menuIconLine3)
        }
    }

    private fun openSidebar(
        sidebar: View,
        menuIconLine1: View,
        menuIconLine2: View,
        menuIconLine3: View
    ) {
        sidebar.visibility = View.VISIBLE
        sidebar.animate().translationX(0f).duration = 350
        ViewCompat.animate(menuIconLine1).rotation(45f).setDuration(350).start()
        ViewCompat.animate(menuIconLine2).alpha(0f).setDuration(350).start()
        ViewCompat.animate(menuIconLine3).rotation(-45f).setDuration(350).start()
        isSidebarOpen = true
    }

    private fun closeSidebar(
        sidebar: View,
        menuIconLine1: View,
        menuIconLine2: View,
        menuIconLine3: View
    ) {
        sidebar.animate().translationX(-200f).duration = 350
        ViewCompat.animate(menuIconLine1).rotation(0f).setDuration(350).start()
        ViewCompat.animate(menuIconLine2).alpha(1f).setDuration(350).start()
        ViewCompat.animate(menuIconLine3).rotation(0f).setDuration(350).start()
        sidebar.postDelayed({
            sidebar.visibility = View.GONE
        }, 350)
        isSidebarOpen = false
    }

    private fun setupMenuClickListeners() {
        val menuItem1: TextView = findViewById(R.id.menu_item_1)
        val menuItem2: TextView = findViewById(R.id.menu_item_2)
        val menuItem3: TextView = findViewById(R.id.menu_item_3)
        val menuItem4: TextView = findViewById(R.id.menu_item_4)
        val menuItem5: TextView = findViewById(R.id.menu_item_5)

        menuItem1.setOnClickListener {
            val intent = Intent(this, NoticeMainActivity::class.java)
            startActivity(intent)
        }

        menuItem2.setOnClickListener {
            val intent = Intent(this, CreateGameMainActivity::class.java)
            startActivity(intent)
        }
        menuItem3.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        menuItem4.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        menuItem5.setOnClickListener {
            val intent = Intent(this, AnimalFaceActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupHeaderClickListeners() {
        val logo: ImageView = findViewById(R.id.logo)
        logo.setOnClickListener {
        }
    }
}
