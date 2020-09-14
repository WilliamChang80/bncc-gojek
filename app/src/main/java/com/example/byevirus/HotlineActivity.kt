package com.example.byevirus

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hotline_bottom_sheet.*
import kotlinx.android.synthetic.main.activity_look_up.*
import lookup.HotlineAdapter
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class HotlineActivity: AppCompatActivity() {



    private val mockHotlineList = mutableListOf(
        HotlineData(name = "Loading...", imgIcon = "", phone = ""),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotline_bottom_sheet)
        val okHttpClient = OkHttpClient()

        val hotlineAdapter = HotlineAdapter(mockHotlineList)
        rvhotline.layoutManager = LinearLayoutManager(this)
        rvhotline.adapter = hotlineAdapter



        val request: Request = Request.Builder()
            .url("https://bncc-corona-versus.firebaseio.com/v1/hotlines.json")
            .build()
        //new call buat request yang di prepare sama okhttp dan enqueue buat jalanin
        okHttpClient.newCall(request).enqueue(getCallback(hotlineAdapter))
    }

    private fun getCallback(hotlineAdapter: HotlineAdapter): Callback {
        //object buat bikin class dari interface
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@HotlineActivity.runOnUiThread {
                    Toast.makeText(this@HotlineActivity, e.message, Toast.LENGTH_SHORT).show()
                }

            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val jsonString: String? = response.body?.string()
                    val jsonArray = JSONArray(jsonString)
                    val hotlineListFromNetwork: MutableList<HotlineData> =
                        mutableListOf<HotlineData>()

                    for (i in 0 until jsonArray.length()) {
                        hotlineListFromNetwork.add(
                            HotlineData(
                                imgIcon = jsonArray.getJSONObject(i).getString("img_icon"),
                                phone = jsonArray.getJSONObject(i).getString("phone"),
                                name = jsonArray.getJSONObject(i).getString("name"),

                                )
                        )
                    }
                    this@HotlineActivity.runOnUiThread {
                        hotlineAdapter.updateData(hotlineListFromNetwork)
                    }

                } catch (e: Exception) {
                    this@HotlineActivity.runOnUiThread {
                        Toast.makeText(this@HotlineActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }
    }

    }
