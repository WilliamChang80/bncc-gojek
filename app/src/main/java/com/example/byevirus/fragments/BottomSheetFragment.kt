package com.example.byevirus.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_hotline_bottom_sheet.*
import lookup.HotlineAdapter
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class BottomSheetFragment: BottomSheetDialogFragment() {

    private val mockHotlineList = mutableListOf(
        HotlineData(name = "Loading...", imgIcon = "", phone = ""),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_hotline_bottom_sheet,container,false)
    }

    //buat tambahin button click listener di dalam di bottom sheet
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val okHttpClient = OkHttpClient()

        val hotlineAdapter = HotlineAdapter(mockHotlineList)
        rvhotline.layoutManager = LinearLayoutManager(context)
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
               //pake activity krn fragment attach di activity
                this@BottomSheetFragment.activity?.runOnUiThread() {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
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
                    this@BottomSheetFragment.activity?.runOnUiThread {
                        hotlineAdapter.updateData(hotlineListFromNetwork)
                    }

                } catch (e: Exception) {
                    this@BottomSheetFragment.activity?.runOnUiThread {
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
     }
    }
