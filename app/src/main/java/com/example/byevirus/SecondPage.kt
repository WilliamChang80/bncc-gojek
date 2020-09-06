package com.example.byevirus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class SecondPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_page)

        val arrow_click_back = findViewById(R.id.ImageView_back) as ImageView

        arrow_click_back.setOnClickListener{
            backToFirstPage()
        }

    }

    private fun backToFirstPage(){
        val intent = Intent(this, MainActivity::class.java).apply{

        }
        startActivity(intent)
    }
}