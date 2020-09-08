package com.example.byevirus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView


class MainActivity : AppCompatActivity() {
    //buat mastiin key tidak berbeda atau tetep sama, seperti static variabel
   companion object{
       const val extra = "Extra"
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrow_click_1 = findViewById(R.id.Image_arrow) as ImageView
        val about_click = findViewById(R.id.Image_info) as ImageView

        arrow_click_1.setOnClickListener{
            openSecondPage()
        }

        about_click.setOnClickListener {
            openAbout()
        }

    }
    private fun openSecondPage(){
        val intent = Intent(this, SecondPage::class.java).apply {
            putExtra(extra, "This is from main activity")
        }
        startActivity(intent)
    }

    private fun openAbout(){
        val intent = Intent(this, about_pop::class.java)
        startActivity(intent)
    }
}