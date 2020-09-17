package com.example.byevirus.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.byevirus.fragment.BottomSheetFragment
import com.example.byevirus.R
import com.example.byevirus.constants.ApiUrl.Companion.HOMEPAGE_API_URL
import com.example.byevirus.fragment.DialogAboutFragment
import com.example.byevirus.model.TotalCase
import okhttp3.*
import org.json.JSONArray
import java.io.IOException


class MainActivity : AppCompatActivity() {

    //buat mastiin key tidak berbeda atau tetep sama, seperti static variabel


    private val okHttpClient = OkHttpClient()

    private val mockHomeList = mutableListOf(
        TotalCase(
            hospitalize = "Loading...",
            positive = " ",
            recovered = " ",
            death = " "
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomSheetFragment = BottomSheetFragment()
        val dialogAboutFragment = DialogAboutFragment()
        val arrow1 = findViewById<ImageView>(R.id.Image_arrow)
        val arrow2 = findViewById<ImageView>(R.id.Image_arrow2)
        val arrow3 = findViewById<ImageView>(R.id.Image_arrow3)
        val about = findViewById<ImageView>(R.id.Image_info)

        val request: Request = Request.Builder()
            .url(HOMEPAGE_API_URL)
            .build()
        okHttpClient.newCall(request).enqueue(getCallback())

        arrow1.setOnClickListener {
            openSecondPage()
        }

        arrow2.setOnClickListener {
            bottomSheetFragment.show(supportFragmentManager, "bottomSheetDialog")
        }

        arrow3.setOnClickListener {
            openSurveyPage()
        }

        about.setOnClickListener {
            dialogAboutFragment.show(supportFragmentManager, "bottomSheetDialog")
        }


    }

    private fun getCallback(): Callback {
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@MainActivity.runOnUiThread {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val jsonString: String? = response.body?.string()
                    val jsonArray = JSONArray(jsonString)
                    val homeListFromNetwork: MutableList<TotalCase> = mutableListOf<TotalCase>()

                    for (i in 0 until jsonArray.length()) {
                        homeListFromNetwork.add(
                            TotalCase(
                                hospitalize = jsonArray.getJSONObject(i).getString("positif"),
                                positive = jsonArray.getJSONObject(i).getString("dirawat"),
                                recovered = jsonArray.getJSONObject(i).getString("sembuh"),
                                death = jsonArray.getJSONObject(i).getString("meninggal")
                            )
                        )
                    }
                    this@MainActivity.runOnUiThread {
                        findViewById<TextView>(R.id.TextView_Jumlah).text =
                            homeListFromNetwork.get(0).hospitalize
                        findViewById<TextView>(R.id.TextView_Number_positive).text =
                            homeListFromNetwork.get(0).positive
                        findViewById<TextView>(R.id.TextView_Number_recovered).text =
                            homeListFromNetwork.get(0).recovered
                        findViewById<TextView>(R.id.TextView_Number_death).text =
                            homeListFromNetwork.get(0).death
//                        lookUpAdapter.updateData(lookUpListFromNetwork)
                    }
                } catch (e: Exception) {
                    this@MainActivity.runOnUiThread {
                        Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    private fun openSecondPage() {
        val intent = Intent(this, LookUpActivity::class.java).apply {
            putExtra("extra", "This is from main activity")
        }
        startActivity(intent)
    }

    private fun openSurveyPage() {
        val intent = Intent(this, survey::class.java).apply {
            putExtra("extra", "This is from main activity")
        }
        startActivity(intent)

    }
}