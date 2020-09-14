package com.example.byevirus.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.byevirus.R
import com.example.byevirus.fragments.AboutFragment


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
        val intent = Intent(this, LookupActivity::class.java).apply {
            putExtra(extra, "This is from main activity")
        }
        startActivity(intent)
    }

    private fun openAbout(){
        val intent = Intent(this, AboutFragment::class.java)
        startActivity(intent)
    }
}