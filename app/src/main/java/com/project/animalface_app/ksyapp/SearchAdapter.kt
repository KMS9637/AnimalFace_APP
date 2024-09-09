package com.project.animalface_app.ksyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.animalface_app.R

class SearchAdapter(private var itemList: List<String>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    // 뷰 홀더 정의
    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

    // 아이템 레이아웃을 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return SearchViewHolder(view)
    }

    // 아이템 데이터를 뷰에 바인딩
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.textView.text = itemList[position]
    }

    // 아이템 개수 반환
    override fun getItemCount(): Int {
        return itemList.size
    }

    // 데이터 업데이트 메서드
    fun updateData(newItemList: List<String>) {
        itemList = newItemList
        notifyDataSetChanged()
    }
}
