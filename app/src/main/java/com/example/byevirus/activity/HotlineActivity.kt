package com.example.byevirus.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.example.byevirus.R
import com.example.byevirus.adapter.HotlineAdapter
import com.example.byevirus.constants.ApiUrl.Companion.HOTLINES_API_URL
import com.example.byevirus.model.Hotline
import kotlinx.android.synthetic.main.activity_hotline_bottom_sheet.*
import okhttp3.*
import org.json.JSONArray
import java.io.IOException


class HotlineActivity : AppCompatActivity() {


    private val mockHotlineList = mutableListOf(
        Hotline(
            name = "Loading...",
            imgIcon = "",
            phone = ""
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotline_bottom_sheet)
        val okHttpClient = OkHttpClient()

        val hotlineAdapter = HotlineAdapter(mockHotlineList)
        rvhotline.layoutManager = LinearLayoutManager(this)
        rvhotline.adapter = hotlineAdapter

        val request: Request = Request.Builder()
            .url(HOTLINES_API_URL)
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
                    val hotlineListFromNetwork: MutableList<Hotline> =
                        mutableListOf<Hotline>()

                    for (i in 0 until jsonArray.length()) {
                        hotlineListFromNetwork.add(
                            Hotline(
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
