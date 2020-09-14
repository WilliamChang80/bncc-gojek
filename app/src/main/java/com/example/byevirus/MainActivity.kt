package com.example.byevirus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.byevirus.lookup.LookUp
import kotlinx.android.synthetic.main.item_hotline.view.*
import lookup.LookUpAdapter
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import kotlin.math.log


class MainActivity : AppCompatActivity() {

    //buat mastiin key tidak berbeda atau tetep sama, seperti static variabel
    companion object {
        const val extra = "Extra"
        const val bottomSheetFragment = "BottomSheetFragment"
    }

    private val okHttpClient = OkHttpClient()

    private val mockHomeList = mutableListOf(
        TotalCases(hospitalize = "Loading...", positive = " ", recovered = " ", death = " "),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomSheetFragment = BottomSheetFragment()
        val arrow_click_1 = findViewById<ImageView>(R.id.Image_arrow)
        val arrow_click_2 = findViewById<ImageView>(R.id.Image_arrow2)

        val request: Request = Request.Builder()
            .url("https://api.kawalcorona.com/indonesia/")
            .build()
        //new call buat request yang di prepare sama okhttp dan enqueue buat jalanin
        okHttpClient.newCall(request).enqueue(getCallback())

//             val close = findViewById<ImageView>(R.id.Image_X)
//
//                close.setOnClickListener {
//            bottomSheetFragment.dismiss()
//        }


        //klik arrow 1 LOOk UP
        arrow_click_1.setOnClickListener {
            openSecondPage()
        }
        // klik arrow 2 hotline
        arrow_click_2.setOnClickListener {
            bottomSheetFragment.show(supportFragmentManager, "bottomSheetDialog")
        }


//        bottomSheetFragment.isCancelable = false
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
                    val homeListFromNetwork: MutableList<TotalCases> = mutableListOf<TotalCases>()
                    Log.d("msg", "hayo")

                    for (i in 0 until jsonArray.length()){
                        homeListFromNetwork.add(
                            TotalCases(
                                hospitalize = jsonArray.getJSONObject(i).getString("positif"),
                                positive = jsonArray.getJSONObject(i).getString("dirawat"),
                                recovered = jsonArray.getJSONObject(i).getString("sembuh"),
                                death = jsonArray.getJSONObject(i).getString("meninggal")
                            )
                        )
                    }
                    this@MainActivity.runOnUiThread {
                        findViewById<TextView>(R.id.TextView_Jumlah).text = homeListFromNetwork.get(0).hospitalize
                        findViewById<TextView>(R.id.TextView_Number_positive).text = homeListFromNetwork.get(0).positive
                        findViewById<TextView>(R.id.TextView_Number_recovered).text = homeListFromNetwork.get(0).recovered
                        findViewById<TextView>(R.id.TextView_Number_death).text = homeListFromNetwork.get(0).death
//                        lookUpAdapter.updateData(lookUpListFromNetwork)
                    }
                }
                catch (e : Exception){
                    this@MainActivity.runOnUiThread{
                        Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    private fun openSecondPage() {
        val intent = Intent(this, LookUpActivity::class.java).apply {
            putExtra(extra, "This is from main activity")
        }
        startActivity(intent)
    }



}