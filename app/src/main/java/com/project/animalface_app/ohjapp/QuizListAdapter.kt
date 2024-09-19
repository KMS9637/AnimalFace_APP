package com.project.animalface_app.ohjapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.animalface_app.R

class QuizListAdapter(
    private val quizList: List<Quiz>,
    private val onQuizClick: (Quiz) -> Unit
) : RecyclerView.Adapter<QuizListAdapter.QuizViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quiz, parent, false)
        return QuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val quiz = quizList[position]
        holder.bind(quiz, onQuizClick)
    }

    override fun getItemCount(): Int = quizList.size

    class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val quizNameTextView: TextView = itemView.findViewById(R.id.quizName)

        fun bind(quiz: Quiz, onQuizClick: (Quiz) -> Unit) {
            quizNameTextView.text = quiz.quiz_name
            itemView.setOnClickListener { onQuizClick(quiz) }
        }
    }
}
