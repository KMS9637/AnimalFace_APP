package com.project.animalface_app.ksyapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.animalface_app.R
import com.project.animalface_app.ohjapp.ksyAPI.CreateGameMainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeMainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    private val retrofitService = RetrofitClient.instance.create(NoticeApiService::class.java)
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NoticeAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_main)

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NoticeAdapter(emptyList()) { notice ->
            showNoticeDetail(notice.noticeNo)
        }
        recyclerView.adapter = adapter

        // Initialize the noticeUI visibility
        findViewById<LinearLayout>(R.id.noticeUI).visibility = View.VISIBLE

        if (savedInstanceState == null) {
            showFragment(NoticeFragment())
        }

        fetchNotices()
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showNoticeDetail(noticeNo: Long) {
        val fragment = DataFragment.newInstance(noticeNo)

        findViewById<LinearLayout>(R.id.noticeUI)?.visibility = View.GONE

        if (supportFragmentManager.findFragmentByTag("DataFragment") == null) {
            showFragment(fragment)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchNotices() {
        retrofitService.getNotices().enqueue(object : Callback<List<Notice>> {
            override fun onResponse(call: Call<List<Notice>>, response: Response<List<Notice>>) {
                if (response.isSuccessful) {
                    val notices = response.body() ?: emptyList()
                    Log.d("FetchNotices", "Notices fetched successfully: ${notices.size} items")
                    runOnUiThread {
                        adapter.updateData(notices)
                    }
                } else {
                    Log.e("FetchNotices", "야 여기 에러남: ${response.code()} ${response.message()}")
                    Log.e("FetchNotices", "Response body: ${response.errorBody()?.string()}")
                    showToast("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Notice>>, t: Throwable) {
                Log.e("FetchNotices", "Failed to fetch data: ${t.message}", t)
                showToast("Failed to fetch data: ${t.message}")
            }
        })
    }



    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showNoticeUI() {
        findViewById<LinearLayout>(R.id.noticeUI)?.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        // Check if there's a fragment to pop
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()

            // Ensure `noticeUI` is visible when returning from fragment
            findViewById<LinearLayout>(R.id.noticeUI)?.visibility = View.VISIBLE
        } else {
            super.onBackPressed()
        }

        val createGameButton: Button = findViewById(R.id.createGame)
        createGameButton.setOnClickListener {
            val intent = Intent(this, CreateGameMainActivity::class.java)
            startActivity(intent)
        }
    }
}
