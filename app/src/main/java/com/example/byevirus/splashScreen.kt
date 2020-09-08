package com.example.byevirus

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class splashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed(
            Runnable
            {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)

                // close this activity
                finish()
            }, 3 * 1000
        )
    }
}