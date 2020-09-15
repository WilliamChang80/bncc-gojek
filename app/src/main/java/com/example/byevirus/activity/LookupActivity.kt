package com.example.byevirus.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.byevirus.R

class LookupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_page)

        val arrowClickBack = findViewById<ImageView>(R.id.ImageView_back)

        arrowClickBack.setOnClickListener {
            backToFirstPage()
        }

    }

    private fun backToFirstPage() {
        val intent = Intent(this, MainActivity::class.java).apply {

        }
        startActivity(intent)
    }
}