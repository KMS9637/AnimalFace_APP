package com.project.animalface_app.ksyapp

import android.os.Bundle
import android.widget.ImageButton
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.animalface_app.R

class SearchActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter

    private val itemList = emptyList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }

        // View 초기화
        searchView = findViewById(R.id.searchView)
        recyclerView = findViewById(R.id.recyclerView)

        // RecyclerView 설정
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SearchAdapter(itemList)
        recyclerView.adapter = adapter

        // SearchView 쿼리 리스너 설정
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = itemList.filter { it.contains(newText ?: "", ignoreCase = true) }
                adapter.updateData(filteredList)
                return true
            }
        })
    }
}