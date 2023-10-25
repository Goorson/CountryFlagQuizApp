package com.quiz.CountryFlagQuiz

import android.content.Intent
import android.icu.lang.UCharacter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.quiz.CountryFlagQuiz.R

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val button: Button = findViewById(R.id.btn_finish)
        val finalText: TextView = findViewById(R.id.tv_score)
        val finalName: TextView = findViewById(R.id.tv_name)

        finalName.text = UCharacter.toUpperCase(intent.getStringExtra(Constants.USER_NAME))
        finalText.text = "You scored ${intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)} out of ${intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)}"

        button.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}